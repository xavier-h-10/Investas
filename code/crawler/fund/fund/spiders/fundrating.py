import scrapy
from ..items import FundRatingItem


class FundRatingSpider(scrapy.Spider):
    name = 'fund_rating'

    allowed_domains = ['eastmoney.com']
    custom_settings = {
        'ITEM_PIPELINES': {
            'fund.pipelines.FundRatingPipeline': 333
        }
    }


    def start_requests(self):
        urls = [
            'http://fund.eastmoney.com/allfund.html',
        ]
        for url in urls:
            yield scrapy.Request(url=url, callback=self.parse)

    def parse(self, response, **kwargs):
        li = response.css('#code_content > div > ul > li')
        for item in li:
            if len(item.css('a::attr(href)')) >= 3:
                yield scrapy.Request(
                    url=item.css('a::attr(href)')[0].get(),
                    callback=self.parse_fund_detail,
                )
        # yield scrapy.Request(
        #     url='http://fundf10.eastmoney.com/007961.html',
        #     callback=self.parse_fund_detail
        # )

    def parse_fund_detail(self, response):
        item = FundRatingItem()
        item['code'] = response.css('#body > div:nth-child(11) > div > div > div.fundDetail-header > div.fundDetail-tit > div > span.ui-num ::text').get()
        if response.css('div.jjpj1').get() is not None:
            item['rating'] = 1
        elif response.css('div.jjpj2').get() is not None:
            item['rating'] = 2
        elif response.css('div.jjpj3').get() is not None:
            item['rating'] = 3
        elif response.css('div.jjpj4').get() is not None:
            item['rating'] = 4
        elif response.css('div.jjpj5').get() is not None:
            item['rating'] = 5
        else:
            item['rating'] = 0
        yield item
