from abc import ABC

import scrapy
import json
from ..items import StockIndex

'''
StockHoldSpider arguments:
mode: 0/1, 0 means crawl all, 1 means crawl specific
fundcode: fund code
command example: scrapy crawl stockhold -a mode=1 -a fundcode=000001
'''


class StockHoldSpider(scrapy.Spider, ABC):
    name = 'stock_index'

    custom_settings = {
        'ITEM_PIPELINES': {
            'fund.pipelines.StockIndexPipeline': 400
        }
    }

    def start_requests(self):
        yield scrapy.Request(
            "https://push2.eastmoney.com/api/qt/stock/get?secid=1.000300&ut=bd1d9ddb04089700cf9c27f6f7426281&fields"
            "=f118,f107,f57,f58,f59,f152,f43,f169,f170,f46,f60,f44,f45,f168,f50,f47,f48,f49,f46,f169,f161,f117,f85,"
            "f47,f48,f163,f171,f113,f114,f115,f86,f117,f85,f119,f120,f121,f122,f292",
            callback=self.parse_index)

    def parse_index(self, response):
        json_obj = json.loads(response.text)
        shanghai300_index = json_obj['data']['f43']/100
        change = json_obj['data']['f170']/100
        index = StockIndex()
        index['stock_id'] = "000300"
        index['stock_name'] = "沪深300"
        index['stock_full_name'] = "沪深300指数"
        index['stock_sort'] = "指数"
        index['stock_price'] = shanghai300_index
        index['newest_rate'] = change
        yield index
