from abc import ABC

import scrapy
import re
from ..items import FundCompanyItem

'''
FundCompanySpider arguments:
no argument
'''


class FundCompanySpider(scrapy.Spider, ABC):
    name = 'company'

    custom_settings = {
        'ITEM_PIPELINES': {
            'fund.pipelines.FundCompanyWriterPipeline': 200
        }
    }

    def start_requests(self):
        """request for all company code"""
        yield scrapy.Request(
            "http://fund.eastmoney.com/company/default.html",
            callback=self.parse_all_company,
        )

    def parse_all_company(self, response):

        all_link = response.xpath('//div[@id=\'companyTable\']//td[@class=\'td-align-left\']/a/@href').getall()
        pattern = re.compile('/Company/[0-9]+.html')

        for link in all_link:

            if pattern.match(link):
                company_code = re.findall('[0-9]+', link)[0]
                company_link = 'http://fund.eastmoney.com/Company/f10/jbgk_{cc}.html'.format(cc=company_code)
                '''request for each link'''
                yield scrapy.Request(
                    company_link,
                    callback=self.parse_company_info,
                    cb_kwargs=dict(company_code=company_code)
                )

    def parse_company_info(self, response, company_code):
        grade = response.xpath('//div[@class=\'common-basic-info\']/div[@class=\'fund-info\']/ul/li[@class=\'rating\']')
        star = grade.xpath('./label[@class=\'star grade iconfont\']').getall()
        star_num = len(star)

        company_info_line = response.xpath('//div[@class=\'company-info\']//tr')
        ch_name = company_info_line[0].xpath('./td[@class=\'category-value\']/text()').get()
        eng_name = company_info_line[1].xpath('./td[@class=\'category-value\']/text()').get()
        website = company_info_line[7].xpath('./td[@class=\'category-value\']/text()').get()

        item = FundCompanyItem()
        item['FundCompanyCode'] = company_code
        item['FundCompanyNameCN'] = ch_name
        item['FundCompanyNameEN'] = eng_name
        item['FundCompanyWebsite'] = website
        item['rate'] = star_num
        yield item
