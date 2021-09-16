import mysql.connector
import scrapy
import pymongo
from scrapy.pipelines.images import ImagesPipeline
import base64
from contextlib import suppress
import hashlib
from scrapy.utils.python import to_bytes
import pymysql
from itemadapter import ItemAdapter


def mysql_save(self, query, data):
    cursor = self.cnx.cursor()
    cursor.execute(query, data)
    self.cnx.commit()
    cursor.close()


class HistoricNetWriterPipeline:
    table = 'fund_daily_info'

    def __init__(self, host, user, passwd, db):
        self.host = host
        self.user = user
        self.passwd = passwd
        self.db = db

    def open_spider(self, spider):
        self.cnx = mysql.connector.connect(user=self.user, password=self.passwd,
                                           host=self.host, database=self.db)
        self.cnx.cursor().execute("SET FOREIGN_KEY_CHECKS=0")
        self.cnx.commit()

    def close_spider(self, spider):
        self.cnx.cursor().execute("SET FOREIGN_KEY_CHECKS=1")
        self.cnx.commit()
        self.cnx.close()

    def process_item(self, item, spider):
        query = ("INSERT INTO {table} (fund_code,NAV,update_date,accumulative_NAV) VALUES (%(FundCode)s, %(NAV)s, "
                 "%(date)s, %(accumulative_value)s) ON DUPLICATE KEY UPDATE NAV=values(NAV),accumulative_NAV=values(accumulative_NAV)".format(table=self.table))
        mysql_save(self, query, dict(item))
        self.cnx.commit()
        return item

    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host=crawler.settings.get('MYSQL_URL'),
            user=crawler.settings.get('MYSQL_USER'),
            passwd=crawler.settings.get('MYSQL_PASSWD'),
            db=crawler.settings.get('MYSQL_DB')
        )


class FundCompanyWriterPipeline:
    table = 'fund_company'

    def __init__(self, host, user, passwd, db):
        self.host = host
        self.user = user
        self.passwd = passwd
        self.db = db

    def open_spider(self, spider):
        self.cnx = mysql.connector.connect(user=self.user, password=self.passwd,
                                           host=self.host, database=self.db)
        self.cnx.cursor().execute("SET FOREIGN_KEY_CHECKS=0")
        self.cnx.commit()

    def close_spider(self, spider):
        self.cnx.cursor().execute("SET FOREIGN_KEY_CHECKS=1")
        self.cnx.commit()
        self.cnx.close()

    def process_item(self, item, spider):
        query = (
            "INSERT INTO {table} (fund_company_code,fund_company_name_CN,fund_company_name_EN,fund_company_website,rate) "
            "VALUES (%(FundCompanyCode)s, %(FundCompanyNameCN)s, %(FundCompanyNameEN)s, %(FundCompanyWebsite)s, "
            "%(rate)s)".format(table=self.table))
        mysql_save(self, query, dict(item))
        return item

    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host=crawler.settings.get('MYSQL_URL'),
            user=crawler.settings.get('MYSQL_USER'),
            passwd=crawler.settings.get('MYSQL_PASSWD'),
            db=crawler.settings.get('MYSQL_DB')
        )


class StockHoldWriterPipeline:
    table = 'fund_portfolio'

    def __init__(self, host, user, passwd, db):
        self.host = host
        self.user = user
        self.passwd = passwd
        self.db = db
        self.cnx = mysql.connector.connect(user=self.user, password=self.passwd,
                                           host=self.host, database=self.db,
                                           auth_plugin='mysql_native_password')
        self.cnx.cursor().execute("SET FOREIGN_KEY_CHECKS=0")
        self.cnx.commit()

    def open_spider(self, spider):
        return

    def close_spider(self, spider):
        self.cnx.cursor().execute("SET FOREIGN_KEY_CHECKS=1")
        self.cnx.commit()
        self.cnx.close()

    def process_item(self, item, spider):
        query = (
        "INSERT INTO {table} (announce_date,fund_code,stock_id,stock_name,stock_mkv,stock_amount,percentage) VALUES (%("
        "announce_date)s, %(fund_code)s, %(stock_id)s, %(stock_name)s,%(stock_mkv)s,%(stock_amount)s,%(percentage)s)".format(
            table=self.table))
        mysql_save(self, query, dict(item))
        return item

    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host=crawler.settings.get('MYSQL_URL'),
            user=crawler.settings.get('MYSQL_USER'),
            passwd=crawler.settings.get('MYSQL_PASSWD'),
            db=crawler.settings.get('MYSQL_DB')
        )


class SaveToMongoPipeline(ImagesPipeline):

    def __init__(self, mongo_url, mongo_db, mongo_collection, store_url, settings):
        super().__init__(store_uri=store_url, settings=settings)
        self.mongo_url = mongo_url
        self.mongo_db = mongo_db
        self.mongo_collection = mongo_collection
        self.store_url = store_url
        self.client = pymongo.MongoClient(self.mongo_url)
        self.db = self.client[self.mongo_db]
        self.db.drop_collection(self.mongo_collection)

    def get_media_requests(self, item, info):
        for image_url in item['src']:
            yield scrapy.Request(image_url)

    def file_path(self, request, response=None, info=None, *, item=None):
        image_guid = hashlib.sha1(to_bytes(item['src'][0])).hexdigest()
        return f'full/{image_guid}.jpg'

    def item_completed(self, results, item, info):
        with suppress(KeyError):
            ItemAdapter(item)[self.images_result_field] = [x for ok, x in results if ok]
        image_guid = hashlib.sha1(to_bytes(item['src'][0])).hexdigest()
        f = open(f'./manager_avatar/full/{image_guid}.jpg', 'rb')
        avatar = base64.b64encode(f.read())
        table = self.db[self.mongo_collection]
        table.insert_one({
            'manager_id': item['manager_id'][0],
            'avatar': avatar
        })
        return item

    def close_spider(self, spider):
        self.client.close()

    @classmethod
    def from_settings(cls, settings):
        return cls(
            mongo_url=settings.get('MONGO_URL'),
            mongo_db=settings.get('MONGO_DB'),
            mongo_collection=settings.get('MONGO_COLLECTION'),
            store_url=settings.get('IMAGES_STORE'),
            settings=settings
        )


class SaveToMysqlPipeline(object):

    def __init__(self, host, user, passwd, db, port, charset):
        self.host, self.user, self.passwd, self.db, self.port, self.charset = host, user, passwd, db, port, charset
        self.conn = pymysql.connect(host=self.host, user=self.user, passwd=self.passwd, db=self.db, port=self.port,
                                    charset=self.charset)
        self.cursor = self.conn.cursor()
        self.cursor.execute("SET FOREIGN_KEY_CHECKS=0")
        self.conn.commit()

    def process_item(self, item, spider):
        insert_sql = 'insert into fund_manager_info(manager_id, fund_company_code, manager_name, start_time, ' \
                     'manager_description, manager_url) VALUES(%s, %s, %s, %s, %s, %s)'
        self.cursor.execute(insert_sql, (item['manager_id'][0], item['fund_company_code'], item['manager_name'],
                                         item['start_time'], item['manager_description'], item['src']))

        insert_sql1 = 'insert into fund_manager_take_office_info(manager_id, fund_code, start_time, end_time, ' \
                      'repay_rate) VALUES(%s, %s, %s, %s, %s)'
        insert_sql2 = 'insert into fund_manager_take_office_info(manager_id, fund_code, start_time, end_time) VALUES' \
                      '(%s, %s, %s, %s)'
        for fund in item['fund_list']:
            if '%' in fund['repay_rate']:
                self.cursor.execute(insert_sql1, (item['manager_id'][0], fund['fund_code'], fund['start_time'],
                                                  fund['end_time'], fund['repay_rate'].split('%')[0]))
            else:
                self.cursor.execute(insert_sql2, (item['manager_id'][0], fund['fund_code'], fund['start_time'],
                                                  fund['end_time']))

        self.conn.commit()
        return item

    def close_spider(self, spider):
        self.cursor.execute("SET FOREIGN_KEY_CHECKS=1")
        self.conn.commit()
        self.cursor.close()
        self.conn.close()

    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host=crawler.settings.get('MYSQL_URL'),
            user=crawler.settings.get('MYSQL_USER'),
            passwd=crawler.settings.get('MYSQL_PASSWD'),
            db=crawler.settings.get('MYSQL_DB'),
            port=crawler.settings.get('MYSQL_PORT'),
            charset=crawler.settings.get('MYSQL_CHARSET'),
        )


class FundInfoPipeline(object):

    def __init__(self, host, user, passwd, db, port, charset):
        self.host, self.user, self.passwd, self.db, self.port, self.charset = host, user, passwd, db, port, charset
        self.conn = pymysql.connect(host=self.host, user=self.user, passwd=self.passwd, db=self.db, port=self.port,
                                    charset=self.charset)
        self.cursor = self.conn.cursor()
        self.cursor.execute("SET FOREIGN_KEY_CHECKS=0")
        self.conn.commit()

    def process_item(self, item, spider):
        insert_sql = 'insert into fund_basic_info(fund_code, fund_name, fund_name_abbr, fund_manager_code, ' \
                     'custodian_name, fund_establish_date, fund_end_date, fund_type, fund_purchase_rate, ' \
                     'discounted_fund_purchase_rate, issue_amount, manage_fee, asset_size, benchmark) VALUES' \
                     '(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)'
        self.cursor.execute(insert_sql, (item['fund_code'], item['fund_name'], item['fund_name_abbr'],
                                         item['fund_manager_code'], item['custodian_name'], item['fund_establish_date'],
                                         item['fund_end_date'], item['fund_type'], item['fund_purchase_rate'],
                                         item['discounted_fund_purchase_rate'], item['issue_amount'],
                                         item['manage_fee'], item['asset_size'], item['benchmark']))

        insert_sql = 'insert into fund_message(message_timestamp, message_type, fund_code, message_title, ' \
                     'message_content) VALUES(%s, %s, %s, %s, %s)'
        if item['fund_establish_date'] == '9999-12-31':
            date = '2021-7-21'
        else:
            date = item['fund_establish_date']
        for msg in item['description']:
            self.cursor.execute(insert_sql, (date, 1, item['fund_code'], msg[0], msg[1]))

        self.conn.commit()
        return item

    def close_spider(self, spider):
        self.cursor.execute("SET FOREIGN_KEY_CHECKS=1")
        self.conn.commit()
        self.cursor.close()
        self.conn.close()

    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host=crawler.settings.get('MYSQL_URL'),
            user=crawler.settings.get('MYSQL_USER'),
            passwd=crawler.settings.get('MYSQL_PASSWD'),
            db=crawler.settings.get('MYSQL_DB'),
            port=crawler.settings.get('MYSQL_PORT'),
            charset=crawler.settings.get('MYSQL_CHARSET'),
        )


class FundRatingPipeline(object):

    def __init__(self, host, user, passwd, db, port, charset):
        self.host, self.user, self.passwd, self.db, self.port, self.charset = host, user, passwd, db, port, charset
        self.conn = pymysql.connect(host=self.host, user=self.user, passwd=self.passwd, db=self.db, port=self.port,
                                    charset=self.charset)
        self.cursor = self.conn.cursor()
        self.cursor.execute("SET FOREIGN_KEY_CHECKS=0")
        self.conn.commit()

    def process_item(self, item, spider):
        update_sql = 'update fund_basic_info set fund_rating = %s where fund_code = %s'
        self.cursor.execute(update_sql, (item['rating'], item['code']))
        self.conn.commit()
        return item

    def close_spider(self, spider):
        self.cursor.execute("SET FOREIGN_KEY_CHECKS=1")
        self.conn.commit()
        self.cursor.close()
        self.conn.close()

    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host=crawler.settings.get('MYSQL_URL'),
            user=crawler.settings.get('MYSQL_USER'),
            passwd=crawler.settings.get('MYSQL_PASSWD'),
            db=crawler.settings.get('MYSQL_DB'),
            port=crawler.settings.get('MYSQL_PORT'),
            charset=crawler.settings.get('MYSQL_CHARSET'),
        )


class JzgsPipeline(object):

    def __init__(self, host, user, passwd, db, port, charset):
        self.host, self.user, self.passwd, self.db, self.port, self.charset = host, user, passwd, db, port, charset
        self.conn = pymysql.connect(host=self.host, user=self.user, passwd=self.passwd, db=self.db, port=self.port,
                                    charset=self.charset)
        self.cursor = self.conn.cursor()
        self.cursor.execute("SET FOREIGN_KEY_CHECKS=0")
        self.conn.commit()

    def process_item(self, item, spider):
        insert_sql = 'insert into fund_estimate(fund_code, estimate_timestamp, NAV_estimate, ' \
                     'increase_estimate) VALUES(%s, %s, %s, %s)'
        self.cursor.execute(insert_sql, (item['code'], item['time'], item['jzgs'], item['gszzl']))

        self.conn.commit()
        return item

    def close_spider(self, spider):
        self.cursor.execute("SET FOREIGN_KEY_CHECKS=1")
        self.cursor.close()
        self.conn.close()

    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host=crawler.settings.get('MYSQL_URL'),
            user=crawler.settings.get('MYSQL_USER'),
            passwd=crawler.settings.get('MYSQL_PASSWD'),
            db=crawler.settings.get('MYSQL_DB'),
            port=crawler.settings.get('MYSQL_PORT'),
            charset=crawler.settings.get('MYSQL_CHARSET'),
        )


class StockIndexPipeline(object):

    def __init__(self, host, user, passwd, db, port, charset):
        self.host, self.user, self.passwd, self.db, self.port, self.charset = host, user, passwd, db, port, charset
        self.conn = pymysql.connect(host=self.host, user=self.user, passwd=self.passwd, db=self.db, port=self.port,
                                    charset=self.charset)
        self.cursor = self.conn.cursor()
        self.cursor.execute("SET FOREIGN_KEY_CHECKS=0")
        self.conn.commit()

    def process_item(self, item, spider):
        insert_sql = "insert into stock_basic_info(stock_id,stock_name,stock_full_name,stock_sort,stock_price," \
                     "newest_rate) values(%s,%s,%s,%s,%s,%s) on duplicate key update stock_price=values(" \
                     "stock_price),newest_rate=values(newest_rate) "
        self.cursor.execute(insert_sql, (item['stock_id'], item['stock_name'], item['stock_full_name'],
                                         item['stock_sort'], item['stock_price'], item['newest_rate']))

        self.conn.commit()
        return

    def close_spider(self, spider):
        self.cursor.execute("SET FOREIGN_KEY_CHECKS=1")
        self.cursor.close()
        self.conn.close()
        return

    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host=crawler.settings.get('MYSQL_URL'),
            user=crawler.settings.get('MYSQL_USER'),
            passwd=crawler.settings.get('MYSQL_PASSWD'),
            db=crawler.settings.get('MYSQL_DB'),
            port=crawler.settings.get('MYSQL_PORT'),
            charset=crawler.settings.get('MYSQL_CHARSET'),
        )


class StockHistoricPipeline(object):

    def __init__(self, host, user, passwd, db, port, charset):
        self.host, self.user, self.passwd, self.db, self.port, self.charset = host, user, passwd, db, port, charset
        self.conn = pymysql.connect(host=self.host, user=self.user, passwd=self.passwd, db=self.db, port=self.port,
                                    charset=self.charset)
        self.cursor = self.conn.cursor()
        self.cursor.execute("SET FOREIGN_KEY_CHECKS=0")
        self.conn.commit()

    def process_item(self, item, spider):
        insert_sql = "insert into stock_daily_info(stock_id,update_date,stock_price,upper_price,lower_price) values(" \
                     "%s,%s,%s,%s,%s) on duplicate key update stock_price=values(stock_price),upper_price=values(" \
                     "upper_price),lower_price=values(lower_price) "
        self.cursor.execute(insert_sql, (item['stock_id'], item['update_date'], item['stock_price'],
                                         item['upper_price'], item['lower_price']))

        self.conn.commit()
        return

    def close_spider(self, spider):
        self.cursor.execute("SET FOREIGN_KEY_CHECKS=1")
        self.cursor.close()
        self.conn.close()
        return

    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            host=crawler.settings.get('MYSQL_URL'),
            user=crawler.settings.get('MYSQL_USER'),
            passwd=crawler.settings.get('MYSQL_PASSWD'),
            db=crawler.settings.get('MYSQL_DB'),
            port=crawler.settings.get('MYSQL_PORT'),
            charset=crawler.settings.get('MYSQL_CHARSET'),
        )
