from abc import ABC

import scrapy
import re
import json
from copyheaders import headers_raw_to_dict
from ..items import HistoricNetValueItem

from scrapy.spidermiddlewares.httperror import HttpError
from twisted.internet.error import DNSLookupError
from twisted.internet.error import TimeoutError, TCPTimedOutError

'''
HistoricNetSpider arguments:
mode: 0/1, 0 means crawl all, 1 means crawl specific
fetchmagic:36500
fundcode: fund code
command example: scrapy crawl netvalue -a mode=1 -a fetchmagic=36500 -a fundcode=000001
'''


class HistoricNetSpider(scrapy.Spider, ABC):
    name = 'netvalue'

    custom_settings = {
        'ITEM_PIPELINES': {
            'fund.pipelines.HistoricNetWriterPipeline': 400
        }
    }

    header = b'''
    Accept: */*
    Accept-Encoding: gzip, deflate
    Accept-Language: zh-CN,zh;q=0.9
    Connection: keep-alive
    Host: fund.eastmoney.com
    Referer: http://fund.eastmoney.com/data/fundranking.html
    User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.122 Safari/537.36
    '''

    def __init__(self, mode=None, fetchmagic=None, fundcode=None, *args, **kwargs):
        """inhere command line arguments, mode & fundCode"""
        super(HistoricNetSpider, self).__init__(*args, **kwargs)
        self.mode = mode
        self.fetchmagic = fetchmagic
        self.fundcode = fundcode

    def start_requests(self):
        print("mode:"+self.mode)
        print("fetchmagic"+self.fetchmagic)

        """crawl all"""
        if int(self.mode) == 0:
            """request for fund code"""
            yield scrapy.Request(
                "http://fund.eastmoney.com/allfund.html",
                callback=self.parse_fund_code)

        elif int(self.mode) == 1:
            total_count = self.fetchmagic
            fund_code = self.fundcode
            yield scrapy.Request(
                "http://api.fund.eastmoney.com/f10/lsjz?callback=jQuery183036648984792081185_1575425405289&"
                "fundCode={fc}"
                "&pageIndex=1&pageSize={tc}".format(fc=fund_code, tc=total_count),
                headers=headers_raw_to_dict(self.header),
                callback=self.parse_fund_earning_perday,
                cb_kwargs=dict(fund_code=fund_code),
                errback=self.errback_logger)

        else:
            print("error mode")


    def parse_fund_code(self, response):
        print("begin all")
        cols = response.xpath('//div[@class=\'data-list m_b\']//div[@id=\'code_content\']//div[@class=\'num_box\']')
        for col in cols:
            funds_link = col.xpath('.//ul[@class=\'num_right\']/li/div/a[1]/@href').getall()
            for fund_link in funds_link:
                '''request for total records number'''
                fund_code = re.findall('[0-9]+', fund_link)[0]
                total_count = self.fetchmagic
                yield scrapy.Request(
                    "http://api.fund.eastmoney.com/f10/lsjz?callback=jQuery183036648984792081185_1575425405289&"
                    "fundCode={fc}"
                    "&pageIndex=1&pageSize={tc}".format(fc=fund_code, tc=total_count),
                    headers=headers_raw_to_dict(self.header),
                    callback=self.parse_fund_earning_perday,
                    cb_kwargs=dict(fund_code=fund_code),
                    errback=self.errback_logger)

    # def test(self, fund_code):
    #     yield scrapy.Request(
    #         "http://api.fund.eastmoney.com/f10/lsjz?callback=jQuery183036648984792081185_1575425405289&"
    #         "fundCode={fc}"
    #         "&pageIndex=1&pageSize={tc}".format(fc=fund_code, tc=100),
    #         headers=headers_raw_to_dict(self.header),
    #         callback=self.parse_fund_earning_perday,
    #         cb_kwargs=dict(fund_code=fund_code),
    #         errback=self.errback_logger)

    # def get_records_count(self, fund_code, total_count):
    #     yield scrapy.Request(
    #         "http://api.fund.eastmoney.com/f10/lsjz?callback=jQuery183036648984792081185_1575425405289&"
    #         "fundCode={fc}"
    #         "&pageIndex=1&pageSize=20".format(fc=fund_code),
    #         headers=headers_raw_to_dict(self.header),
    #         callback=self.parse_records_count,
    #         cb_kwargs=dict(total_count=total_count))
    #
    # def parse_records_count(self, response, total_count):
    #     print("count")
    #     response = response.text
    #     data = re.findall(r'\((.*?)\)$', response)[0]
    #     data = json.loads(data)
    #     total_count = data.get("TotalCount")

    def parse_fund_earning_perday(self, response, fund_code):
        response = response.text
        data = re.findall(r'\((.*?)\)$', response)[0]
        data = json.loads(data)
        for i in data.get("Data").get("LSJZList"):
            net_value = HistoricNetValueItem()
            net_value['FundCode'] = fund_code
            net_value['date'] = i.get("FSRQ")
            net_value['NAV'] = i.get("DWJZ")
            net_value['accumulative_value'] = i.get("LJJZ")
            # net_value['rate_day'] = i.get("JZZZL")
            # net_value['buy_status'] = i.get("SGZT")
            # net_value['sell_status'] = i.get("SHZT")
            # net_value['profit'] = i.get("FHSP")
            yield net_value

    def errback_logger(self, failure):
        self.logger.error(repr(failure))

        if failure.check(HttpError):
            response = failure.value.response
            self.logger.error('HttpError on %s', response.url)

        elif failure.check(DNSLookupError):
            request = failure.request
            self.logger.error('DNSLookupError on %s', request.url)

        elif failure.check(TimeoutError, TCPTimedOutError):
            request = failure.request
            self.logger.error('TimeoutError on %s', request.url)
