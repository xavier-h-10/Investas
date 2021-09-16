# https://push2his.eastmoney.com/api/qt/stock/kline/get?secid=1.000300&fields1=f1%2Cf2%2Cf3%2Cf4%2Cf5&fields2=f51%2Cf52%2Cf53%2Cf54%2Cf55%2Cf56%2Cf57%2Cf58&klt=101&fqt=0&beg=19900101&end=20220101

from abc import ABC

import scrapy
import json
from ..items import StockHistoricItem

'''
StockHoldSpider arguments:
mode: 0/1, 0 means crawl all, 1 means crawl specific date
secid: market.stock_id
command example: scrapy crawl stock_historic -a mode=1 -a secid=1.000300 -a begin_date=20210818 -a end_date=20210820
'''


class StockHistoricSpider(scrapy.Spider, ABC):
    name = 'stock_historic'

    custom_settings = {
        'ITEM_PIPELINES': {
            'fund.pipelines.StockHistoricPipeline': 400
        }
    }

    def __init__(self, mode=None, secid=None, begin_date=None, end_date=None, *args, **kwargs):
        super(StockHistoricSpider, self).__init__(*args, **kwargs)
        self.mode = mode
        self.secid = secid
        self.begin_date = begin_date
        self.end_date = end_date

    def start_requests(self):
        if int(self.mode) == 0:
            yield scrapy.Request(
                "https://push2his.eastmoney.com/api/qt/stock/kline/get?secid={secid}&fields1=f1,f2,f3,f4,f5&fields2=f51,"
                "f52,f53,f54,f55,f56,f57,f58&klt=101&fqt=0&beg=19900101&end=20220101".format(secid=self.secid),
                callback=self.parse_index)
        elif int(self.mode) == 1:
            yield scrapy.Request(
                "https://push2his.eastmoney.com/api/qt/stock/kline/get?secid={secid}&fields1=f1,f2,f3,f4,"
                "f5&fields2=f51,f52,f53,f54,f55,f56,f57,f58&klt=101&fqt=0&beg={begin_date}&end={end_date}".format(secid=self.secid, begin_date=self.begin_date, end_date=self.end_date),
                callback=self.parse_index)

    def parse_index(self, response):
        json_obj = json.loads(response.text)
        stock_id = json_obj['data']['code']
        klines_list = json_obj['data']['klines']
        for daily_info in klines_list:
            historic_list = daily_info.split(',')
            historic = StockHistoricItem()
            historic['stock_id'] = stock_id
            historic['update_date'] = historic_list[0]
            historic['stock_price'] = historic_list[2]
            historic['upper_price'] = historic_list[3]
            historic['lower_price'] = historic_list[4]
            yield historic
