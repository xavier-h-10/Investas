from abc import ABC

import scrapy
import json
from ..items import JzgsItem

def trim(s):
    if s is None:
        return ""
    else:
        ret = s
        ret.replace("\r", "")
        ret.replace("\n", "")
        return ret.strip()


class JzgsSpider(scrapy.Spider, ABC):
    name = 'jzgs'

    allowed_domains = ['eastmoney.com']
    cookies = {
        'AUTH_FUND.EASTMONEY.COM_GSJZ': 'AUTH*TTJJ*TOKEN',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.164 Safari/537.36',
    }
    custom_settings = {
        'ITEM_PIPELINES': {
            'fund.pipelines.JzgsPipeline': 301,
        }
    }


    def start_requests(self):
        urls = [
            'http://fund.eastmoney.com/js/fundcode_search.js',
        ]
        for url in urls:
            yield scrapy.Request(
                url=url,
                callback=self.parse,
                cookies=self.cookies,
                dont_filter=True
            )

    def parse(self, response, **kwargs):
        data = response.text
        data = data.replace('var r = ', '')
        data = data.replace(';', '')
        data = json.loads(data)
        # data = [["511980"]]
        for fund in data:
            yield scrapy.Request(
                url='https://fundgz.1234567.com.cn/js/{}.js'.format(fund[0]),
                callback=self.parse_one,
                cookies=self.cookies,
                dont_filter=True
            )

    def parse_one(self, response, **kwargs):
        item = JzgsItem()
        data = trim(response.text)
        data = data.replace('jsonpgz(', '')
        data = data.replace(');', '')
        if data != '':
            data = eval(data)
            item['code'] = data.get('fundcode')
            item['jzgs'] = data.get('gsz')
            item['gszzl'] = data.get('gszzl')
            item['time'] = self.cur_time
            if item['jzgs'] is None:
                item['jzgs'] = '-99.99'
            if item['gszzl'] is None:
                item['gszzl'] = '-99.99'
            yield item

