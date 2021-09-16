import scrapy
from ..items import ManagerInfoItem


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


class ManagerInfoSpider(scrapy.Spider):
    name = 'manager_info'
    allowed_domains = ['eastmoney.com']
    custom_settings = {
        'ITEM_PIPELINES': {
            'fund.pipelines.SaveToMysqlPipeline': 300,
        }
    }
    start_urls = ['http://fund.eastmoney.com/Data/FundDataPortfolio_Interface.aspx?' \
                  'dt=14&mc=returnjson&ft=all&pn=50&pi=1&sc=abbname&st=asc']

    def start_requests(self):
        for url in self.start_urls:
            yield scrapy.Request(url=url, callback=self.parse, dont_filter=True)

    def parse(self, response, **kwargs):
        data = response.text
        data = data.replace('var returnjson= ', '')
        data = data.replace('record', '"record"')
        data = data.replace('pages', '"pages"')
        data = data.replace('curpage', '"curpage"')
        data = data.replace('data', '"data"')
        data = eval(data)
        pages = data.get("pages")
        for i in range(pages):
            yield scrapy.Request(
                url="http://fund.eastmoney.com/Data/FundDataPortfolio_Interface.aspx?" \
                    "dt=14&mc=returnjson&ft=all&pn=50&pi={}&sc=abbname&st=asc".format(i + 1),
                callback=self.parse_manager_list,
                dont_filter=True
            )

    def parse_manager_list(self, response):
        data = response.text
        data = data.replace('var returnjson= ', '')
        data = data.replace('data', '"data"')
        data = data.replace('record', '"record"')
        data = data.replace('pages', '"pages"')
        data = data.replace('curpage', '"curpage"')
        data = eval(data)
        for item in data.get("data"):
            id = item[0]
            yield scrapy.Request(
                "http://fund.eastmoney.com/manager/{}.html".format(id),
                callback=self.parse_manager_detail,
                dont_filter=True
            )

    def parse_manager_detail(self, response):
        fund_list = []
        for tr in response.css('body > div:nth-child(8) > div.content_out > div:nth-child(1) > table > tbody > tr'):
            td = tr.css("td")
            start_time = trim(td[5].css('::text').get().split('~', 1)[0])
            end_time = trim(td[5].css('::text').get().split('~', 1)[1])
            if end_time == "至今":
                end_time = "9999-12-31"
                fund_list.append({
                    "fund_code": fund_id(td.css('a::attr(href)').get()),  # 基金代码
                    "资金规模": td[4].css('span.redText::text').get(),
                    "任职时间": td[5].css('::text').get(),
                    "start_time": start_time,
                    "end_time": end_time,
                    "repay_rate": trim(td[7].css('::text').get()).replace(',', ''),  # 任职回报
                })
        item = ManagerInfoItem()
        src = response.css('#photo::attr(src)').get()
        if src[0] == '/':
            src = 'https:' + src
        item['src'] = trim(src)
        item['manager_id'] = manager_id(response.css('table.ftrs')[0].css('a::attr(href)').getall()[0]),
        item['manager_name'] = trim(response.css('h3#name_1::text').get())
        item['manager_description'] = trim(response.css('div.right.ms p::text').getall()[1])
        if len(response.css('div.right.jd::text').getall()) == 6:
            item['start_time'] = trim(response.css('div.right.jd::text').getall()[2])  # 任职起始日期
        else:
            item['start_time'] = trim(response.css('div.right.jd::text').getall()[3])  # 任职起始日期
        item['fund_company_code'] = response.css('div.right.jd a::text').get()
        item['fund_list'] = fund_list
        yield item
