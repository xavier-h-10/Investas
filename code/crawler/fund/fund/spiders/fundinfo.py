import scrapy
import datetime
from ..items import FundInfoItem

def trim(s):
    if s is None:
        return ""
    else:
        ret = s
        ret.replace("\r", "")
        ret.replace("\n", "")
        return ret.strip()


def fund_id(s):
    if s is None:
        return ""
    else:
        return s[-11: -5]


def company_id(s):
    if s is None:
        return ""
    else:
        return s[-13: -5]


def manager_id(s):
    if s is None:
        return ""
    else:
        return s[-13: -5]


def trans(response):
    if len(response.css('span')) < 3:
        return[response.css('::text').get()[0: -4], response.css('::text').get()[0: -4]]
    else:
        return[response.css('span::text').getall()[0][0: -4], response.css('span::text').getall()[2][0: -4]]


class FundInfoSpider(scrapy.Spider):
    name = 'fund_info'

    allowed_domains = ['eastmoney.com']
    custom_settings = {
        'ITEM_PIPELINES': {
            'fund.pipelines.FundInfoPipeline': 300
        }
    }
    fund_type = {'债券型-混合债': 0, '混合型-偏股': 1, '指数型-股票': 2, '混合型-灵活': 3, 'QDII': 4, '债券型-长债': 5,
                 '货币型': 6, '股票型': 7, '混合型-偏债': 8, '混合型-平衡': 9, '债券型-中短债': 10, '商品（不含QDII）': 11,
                 '债券型-可转债': 12, 'REITs': 13}


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
                    url=item.css('a::attr(href)')[2].get(),
                    callback=self.parse_fund_detail,
                )
        # yield scrapy.Request(
        #     url='http://fundf10.eastmoney.com/007961.html',
        #     callback=self.parse_fund_detail
        # )

    def parse_fund_detail(self, response):
        table = response.css('table.info.w790')
        td = table.css('td')

        manager_list = []
        for manager_ref in td[10].css('::attr(href)').getall():
            manager_list.append(manager_id(manager_ref))

        div = response.css('#bodydiv > div:nth-child(12) > div.r_cont.right > div.detail > div.txt_cont > div')
        description = []
        for item in div.css('div.boxitem.w790'):
            description.append([item.css('label.left::text').get(), trim(item.css('p::text').get())])

        fpr = trans(td[16])

        item = FundInfoItem()
        item['fund_code'] = trim(td[2].css('::text').get())[0: 6]                                   # 基金代码
        item['fund_name'] = trim(td[0].css('::text').get())                                         # 基金全称
        item['fund_name_abbr'] = trim(td[1].css('::text').get())                                    # 基金简称
        item['fund_manager_code'] = company_id(trim(td[8].css('::attr(href)').get()))               # 基金管理人
        item['custodian_name'] = trim(td[9].css('::text').get())                                    # 基金托管人（公司名）
        item['fund_establish_date'] = trim(td[5].css('::text').get().split('/', 1)[0])              # 成立日期
        item['fund_establish_date'] = item['fund_establish_date'].replace('年', '-').replace('月', '-').replace('日', '')
        if item['fund_establish_date'] == '':
            item['fund_establish_date'] = '9999-12-31'
        item['fund_end_date'] = '9999-12-31'                                                        # 成立日期
        item['fund_type'] = trim(td[3].css('::text').get())                                         # 基金类型
        if self.fund_type.get(item['fund_type']) is None:
            item['fund_type'] = 14
        else:
            item['fund_type'] = self.fund_type[item['fund_type']]
        item['fund_purchase_rate'] = trim(fpr[0].split('%')[0])                                     # 最高申购费率
        if item['fund_purchase_rate'] == '':
            item['fund_purchase_rate'] = '0'
        item['discounted_fund_purchase_rate'] = trim(fpr[1].split('%')[0])                          # 折后最高申购费率
        if item['discounted_fund_purchase_rate'] == '':
            item['discounted_fund_purchase_rate'] = '0'
        item['issue_amount'] = trim(td[7].css('::text').get().split('亿', 1)[0]).replace(',', '')   # 份额规模
        item['manage_fee'] = trim(td[12].css('::text').get())[0: -4].split('%')[0]                  # 管理费率
        if item['manage_fee'] == '':
            item['manage_fee'] = '0'
        item['asset_size'] = trim(td[6].css('::text').get().split('亿', 1)[0]).replace(',', '')     # 资产规模
        item['benchmark'] = trim(td[18].css('::text').get())                                        # 业绩比较基准
        item['description'] = description                                                           # 备注
        for key in item:
            if type(item[key]) is str:
                item[key] = item[key].replace('---', '0')
        fund_establish_date = datetime.datetime.strptime(item['fund_establish_date'], '%Y-%m-%d').date()
        std = datetime.datetime.strptime('2021-07-01', '%Y-%m-%d').date()
        if item['fund_type'] < 12 and fund_establish_date <= std:
            yield item
        # '发行日期': trim(td[4].css('::text').get()),
        # '规模': trim(td[5].css('::text').get().split('/', 1)[1][0: -2]),
        # '基金经理人': manager_list,
        # '成立来分红': trim(td[11].css('::text').get()),
        # '托管费率': trim(td[13].css('::text').get())[0: -4],
        # '销售服务费率': trim(td[14].css('::text').get())[0: -4],
        # '最高认购费率': trans(td[15]),
        # '最高赎回费率': trim(td[17].css('::text').get())[0: -4],
        # '跟踪标的': trim(td[19].css('::text').get()),
