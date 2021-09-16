from abc import ABC

import scrapy
import re
import json
from copyheaders import headers_raw_to_dict
from ..items import HistoricNetValueItem, FundStockItem

from scrapy.spidermiddlewares.httperror import HttpError
from twisted.internet.error import DNSLookupError
from twisted.internet.error import TimeoutError, TCPTimedOutError

'''
StockHoldSpider arguments:
mode: 0/1, 0 means crawl all, 1 means crawl specific
fundcode: fund code
command example: scrapy crawl stockhold -a mode=1 -a fundcode=000001
'''


class StockHoldSpider(scrapy.Spider, ABC):
    name = 'stockhold'

    custom_settings = {
        'ITEM_PIPELINES': {
            'fund.pipelines.StockHoldWriterPipeline': 400
        }
    }

    header = b'''
    Accept: */*
    Accept-Encoding: gzip, deflate
    Accept-Language: zh,en;q=0.9,zh-TW;q=0.8,en-US;q=0.7
    Connection: keep-alive
    Host: fundf10.eastmoney.com
    User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36
    '''

    def __init__(self, mode=None, fundcode=None, *args, **kwargs):
        """inhere command line arguments, mode & fundCode"""
        super(StockHoldSpider, self).__init__(*args, **kwargs)
        self.mode = mode
        self.fundcode = fundcode

    def start_requests(self):
        """crawl all"""
        if int(self.mode) == 0:
            """request for fund code"""
            yield scrapy.Request(
                "http://fund.eastmoney.com/allfund.html",
                callback=self.parse_fund_code)
        elif int(self.mode) == 1:
            fund_code = self.fundcode
            yield scrapy.Request(
                "http://fundf10.eastmoney.com/FundArchivesDatas.aspx?type=jjcc&code={fc}"
                "&topline=10&year=&month=".format(fc=fund_code),
                headers=headers_raw_to_dict(self.header),
                callback=self.parse_fund_stock,
                cb_kwargs=dict(fund_code=fund_code))
        else:
            print("error mode")

    def parse_fund_code(self, response):
        print("begin all")
        cols = response.xpath('//div[@class=\'data-list m_b\']//div[@id=\'code_content\']//div[@class=\'num_box\']')
        if cols is None:
            return
        for col in cols:
            funds_link = col.xpath('.//ul[@class=\'num_right\']/li/div/a[1]/@href').getall()
            for fund_link in funds_link:
                '''request for total records number'''
                fund_code = re.findall('[0-9]+', fund_link)[0]
                yield scrapy.Request(
                    "http://fundf10.eastmoney.com/FundArchivesDatas.aspx?type=jjcc&code={fc}"
                    "&topline=10&year=&month=".format(fc=fund_code),
                    headers=headers_raw_to_dict(self.header),
                    callback=self.parse_fund_stock,
                    cb_kwargs=dict(fund_code=fund_code),
                    errback=self.errback_logger)

    def parse_fund_stock(self, response, fund_code):
        """爬取最新季度的信息"""
        time = response.xpath('//div[@class=\'boxitem w790\']')[0].xpath('./h4/label')[1].xpath('./font/text()').get()
        table = response.xpath('//table[@class=\'w782 comm tzxq\']')[0].xpath('./tbody/tr')
        for line in table:
            cells = line.xpath('./td')
            """股票代码"""
            stock_code = cells[1].xpath('./a/text()').get()
            """股票名称"""
            stock_name = cells[2].xpath('./a/text()').get()
            """占净值比例"""
            net_percent = cells[6].xpath('./text()').get()
            """单位是万股"""
            quantity = cells[7].xpath('./text()').get()
            """持仓市值"""
            value = cells[8].xpath('./text()').get()

            """remove delimiter of text"""
            quantity = quantity.replace(",", "")
            value = value.replace(",", "")

            if re.match(r'[0-9]*[(.SH)|(.SZ)]', stock_code):
                stock_code = stock_code[:-3]

            fund_stack = FundStockItem()
            fund_stack['announce_date'] = time
            fund_stack['fund_code'] = fund_code
            fund_stack['stock_id'] = stock_code
            fund_stack['stock_name'] = stock_name
            fund_stack['stock_mkv'] = value
            fund_stack['stock_amount'] = quantity
            fund_stack['percentage'] = net_percent[:-1]
            yield fund_stack

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
