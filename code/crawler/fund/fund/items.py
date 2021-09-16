# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class HistoricNetValueItem(scrapy.Item):
    FundCode = scrapy.Field()
    date = scrapy.Field()
    NAV = scrapy.Field()
    accumulative_value = scrapy.Field()
    # rate_day = scrapy.Field()
    # buy_status = scrapy.Field()
    # sell_status = scrapy.Field()
    # profit = scrapy.Field()


class FundCompanyItem(scrapy.Item):
    FundCompanyCode = scrapy.Field()
    FundCompanyNameCN = scrapy.Field()
    FundCompanyNameEN = scrapy.Field()
    FundCompanyWebsite = scrapy.Field()
    rate = scrapy.Field()


class FundStockItem(scrapy.Item):
    announce_date = scrapy.Field()
    fund_code = scrapy.Field()
    stock_id = scrapy.Field()
    stock_name = scrapy.Field()
    stock_mkv = scrapy.Field()
    stock_amount = scrapy.Field()
    percentage = scrapy.Field()


class ManagerInfoItem(scrapy.Item):
    src = scrapy.Field()
    manager_id = scrapy.Field()
    manager_name = scrapy.Field()
    manager_description = scrapy.Field()
    start_time = scrapy.Field()
    fund_company_code = scrapy.Field()
    fund_list = scrapy.Field()


class FundInfoItem(scrapy.Item):
    fund_code = scrapy.Field()
    fund_name = scrapy.Field()
    fund_name_abbr = scrapy.Field()
    fund_manager_code = scrapy.Field()
    custodian_name = scrapy.Field()
    fund_establish_date = scrapy.Field()
    fund_end_date = scrapy.Field()
    fund_type = scrapy.Field()
    fund_purchase_rate = scrapy.Field()
    discounted_fund_purchase_rate = scrapy.Field()
    issue_amount = scrapy.Field()
    manage_fee = scrapy.Field()
    asset_size = scrapy.Field()
    benchmark = scrapy.Field()
    description = scrapy.Field()


class FundRatingItem(scrapy.Item):
    code = scrapy.Field()
    rating = scrapy.Field()


class JzgsItem(scrapy.Item):
    code = scrapy.Field()
    jzgs = scrapy.Field()
    gszzl = scrapy.Field()
    time = scrapy.Field()


class StockIndex(scrapy.Item):
    stock_id = scrapy.Field()
    stock_name = scrapy.Field()
    stock_full_name = scrapy.Field()
    stock_sort = scrapy.Field()
    stock_price = scrapy.Field()
    newest_rate = scrapy.Field()


class StockHistoricItem(scrapy.Item):
    stock_id = scrapy.Field()
    update_date = scrapy.Field()
    stock_price = scrapy.Field()
    upper_price = scrapy.Field()
    lower_price = scrapy.Field()
