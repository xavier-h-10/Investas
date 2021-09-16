use fundSystemTest;
SET FOREIGN_KEY_CHECKS = 0;

# 提醒：在ref里面提示是str的，会暂时给定一个varchar长度，后面根据爬下来的实际数据更改
-- ----------------------------
-- Key table: basic params for fund
-- reference: fundParams.pdf
-- reference: https://waditu.com/document/2?doc_id=19
-- ----------------------------
DROP TABLE IF EXISTS `fund_basic_info`;
CREATE TABLE `fund_basic_info`
(
#    512850
    `fund_code`                     varchar(6)                   NOT NULL,
#    中信建投北京50ETF
    `fund_name`                     varchar(120)                 NOT NULL,
#     未提供例子
    `fund_name_abbr`                varchar(120)                 NOT NULL,

#     基金管理人，外键，指向FundManager表的主键
#     中信建投基金
    `fund_manager_code`             varchar(8)                   NOT NULL,

#     招商银行
#     基金托管人名字，仅存在名字，不能查看详细信息（即到此为止）
    `custodian_name`                varchar(64)                  NOT NULL,

#     币种，可以不爬，直接用CNY
    `currency_type`                 varchar(3)     DEFAULT 'CNY',

#     如果基金合同签订时间未提供，基金没有规定的停止时间，那么
#     根据监管规定应填写99991231，后端处理为不存在
#     2018-09-27
    `fund_establish_date`           DATE                         NOT NULL,
    `fund_end_date`                 DATE                         NOT NULL,

#     外键，指向fund_type的主键，两张表相互持有，但是把fund type的循环加载去掉
#     主要目的是为了机器学习时分类构建模型
#     '债券型-混合债': 0, '混合型-偏股': 1, '指数型-股票': 2, '混合型-灵活': 3, 'QDII': 4, '债券型-长债': 5,
#     '货币型': 6, '股票型': 7， '混合型-偏债': 8, '混合型-平衡': 9, '债券型-中短债': 10, '商品（不含QDII）': 11,
#     '债券型-可转债': 12, 'REITs': 13, ''(other type): 14
    `fund_type`                     int                          NOT NULL,

#     应对变化需求： 基金状态码， 0表示一切正常
    `fund_status`                   int            DEFAULT 0,

#     以下信息不在fundParams中
#     基金申购费率，xx.xxx，用Decimal表示
    `fund_purchase_rate`            decimal(5, 3)                NOT NULL,

#     优惠后基金申购费率，xx.xxx，用Decimal表示
    `discounted_fund_purchase_rate` decimal(5, 3)                NOT NULL,

#      发行份额，以*亿份*为单位
    `issue_amount`                  decimal(20, 6) DEFAULT 0,

#     管理费，指每个单位时间（？）内收取的管理费用份额
    `manage_fee`                    decimal(5, 2)  DEFAULT 0,

#     资产规模，亿元
    `asset_size`                    decimal(20, 6) DEFAULT 0,

#     业绩比较基准，（可能会再建立一张表，专门储存各类指数，
#     那么这里可以存一个benchmark
    `benchmark`                     varchar(255),

    `has_model`                     boolean        DEFAULT FALSE NOT NULL,

    `fund_rating`                   int             DEFAULT 0,

#     约束与主键
    PRIMARY KEY (`fund_code`),
--     FOREIGN KEY (fund_manager_code) REFERENCES fund_manager_info(manager_id),
    CHECK ( discounted_fund_purchase_rate <= 99.999 AND fund_purchase_rate <= 99.999)

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# 创建索引：基金名字
CREATE INDEX FundNameIndex on fund_basic_info (fund_name);
# ...more, 取决于业务场景

-- ----------------------------
-- table: fund type & fund code
-- point out the best fund code model to specific fund type
-- ----------------------------
DROP TABLE IF EXISTS `fund_type_to_model`;
CREATE TABLE `fund_type_to_model`
(
    `fund_type` int        NOT NULL,
    `fund_code` varchar(6) NOT NULL,

    PRIMARY KEY (`fund_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- ----------------------------
-- Key table: daily updated params for fund
-- reference: fundParams.pdf
-- ----------------------------
DROP TABLE IF EXISTS `fund_daily_info`;
CREATE TABLE `fund_daily_info`
(
    `info_id`              bigint auto_increment NOT NULL,
#     外键
    `fund_code`            varchar(6)            NOT NULL,

#     两位小数，用 decimal(16, 2)表示基金份数
    `total_fund_vol`       decimal(16, 2) DEFAULT 0,

#     四位小数，用decimal(7, 4)表示基金单位净值
    `NAV`                  decimal(7, 4)  DEFAULT 0,

#     当前基金日报信息的时间，用date表示，为YYYY-MM-DD, if
#     use insert, format 'YYYY-MM-DD'
    `update_date`          date           DEFAULT '9999-12-31',

#     四位小数，用decimal(7, 4)表示基金累计净值
    `accumulative_NAV`     decimal(7, 4)  DEFAULT 0,

#     两位小数，用decimal(16， 2)表示基金累计单位分红
    `accumulative_per_div` decimal(16, 2) DEFAULT 0,

#     两位小数，用decimal(16， 2)表示基金当日金额总规模
    `fund_size`            decimal(16, 2) DEFAULT 0,

#     应对变化需求： 基金日更信息状态码， 0 表示一切正常
    `fund_status`          int            DEFAULT 0,

    PRIMARY KEY (`info_id`),
    FOREIGN KEY (`fund_code`) REFERENCES fund_basic_info (fund_code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX fund_code ON fund_daily_info (fund_code);
CREATE INDEX update_date ON fund_daily_info (update_date);
CREATE UNIQUE INDEX daily_record ON fund_daily_info (fund_code, update_date);
-- ----------------------------
-- Key table: fund company data，基金管理人（法人）
-- reference: http://fund.eastmoney.com/Company/80000229.html
-- reference: https://waditu.com/document/2?doc_id=118
-- ----------------------------
DROP TABLE IF EXISTS `fund_company`;
CREATE TABLE `fund_company`
(
#     基金公司代号，string(8)表示
    `fund_company_code`        varchar(8)   NOT NULL,

#     基金公司中文名，string(160)表示
    `fund_company_name_CN`     varchar(160) NOT NULL,
#     基金公司英文名，string(160)表示
    `fund_company_name_EN`     varchar(160),

#     基金公司官方网站
    `fund_company_website`     varchar(160),
#     基金公司地址
    `fund_company_address`     varchar(160),
#     基金公司联络电话
    `fund_company_tel`         varchar(40),

#     最好爬下来：注册资本
    `fund_company_reg_capital` decimal(30, 10),

#     评分，默认满分10分，将以半星星呈现，没有评分后台存储-1
    `rate`                     int DEFAULT -1,
#     员工总数
    `employees`                int DEFAULT 0,

#     应对变化需求： 基金公司信息状态码， 0 表示一切正常
    `fund_company_status`      int DEFAULT 0,

    PRIMARY KEY (`fund_company_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX CompanyName ON fund_company (fund_company_name_CN);

-- ----------------------------
-- Key table: fund manager info
-- reference: https://fund.eastmoney.com/manager/30277862.html
-- reference: https://waditu.com/document/2?doc_id=208
-- ----------------------------
DROP TABLE IF EXISTS `fund_manager_info`;
CREATE TABLE `fund_manager_info`
(
    `manager_id`          varchar(8)   NOT NULL,

#     外键，所属公司代码
    `fund_company_code`   varchar(255) NOT NULL,
    `manager_name`        varchar(12)  NOT NULL,
    `start_time`          DATE         NOT NULL,

    `manager_description` varchar(4000) DEFAULT '',

    #     应对变化需求： 基金经理信息状态码， 0 表示一切正常
    `fund_manager_status` int           DEFAULT 0,

    `manager_url`         varchar(1000) DEFAULT '',

    PRIMARY KEY (`manager_id`),
    FOREIGN KEY (fund_company_code) REFERENCES fund_company (fund_company_code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX ManagerName ON fund_manager_info (manager_name);


-- ----------------------------
-- Key table: fund manager take office info
-- reference: https://waditu.com/document/2?doc_id=208
-- ----------------------------
DROP TABLE IF EXISTS `fund_manager_take_office_info`;
CREATE TABLE `fund_manager_take_office_info`
(
    `info_id`                         bigint AUTO_INCREMENT NOT NULL,
#     外键，基金代号和基金经理代号
    `manager_id`                      varchar(8)            NOT NULL,
    `fund_code`                       varchar(6)            NOT NULL,
    `start_time`                      DATE                  NOT NULL,
#     if until now, just write 9999-12-31
#     如果endTime != 9999-12-31，
#     那就不需要重新爬取，否则最好每天更新一次这些费率
    `end_time`                        DATE DEFAULT '9999-12-31',

#     就职以来的回报率
    `repay_rate`                      Decimal(8, 2),

#     应对变化需求： 基金经理就职信息状态码， 0 表示一切正常
    `fund_manager_take_office_status` int  DEFAULT 0,

    PRIMARY KEY (`info_id`),
    FOREIGN KEY (manager_id) REFERENCES fund_manager_info (manager_id),
    FOREIGN KEY (fund_code) REFERENCES fund_basic_info (fund_code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- ----------------------------
-- Table structure for user
-- status int = 0(normal)
-- ----------------------------
DROP TABLE IF EXISTS `stock_basic_info`;
CREATE TABLE `stock_basic_info`
(
#     unique and
    `stock_id`        varchar(8)    NOT NULL,
    `stock_name`      varchar(255)  NOT NULL,
    `stock_full_name` varchar(255)  NOT NULL,

#     现在考虑不建一张新的表存类型
#      所属行业，example: 军工 etc
    `stock_sort`      varchar(40)   NOT NULL,

#    股价
    `stock_price`     decimal(7, 2) NOT NULL,

#      不带百分号，最新涨跌幅，更新时直接覆盖
    `newest_rate`     decimal(5, 2) NOT NULL,

#     应对需求变化，为0时表示股票信息一切正常
    `status`          int DEFAULT 0,


-- TODO: wait to implement: more user's info

    PRIMARY KEY (`stock_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table: stock_daily_info
-- ----------------------------
DROP TABLE IF EXISTS `stock_daily_info`;
CREATE TABLE `stock_daily_info`
(
    `info_id`     bigint auto_increment NOT NULL,

    `stock_id`    varchar(8)            NOT NULL,
#     use insert, format 'YYYY-MM-DD'
    `update_date` date DEFAULT '9999-12-31',
# 	收盘价
    `stock_price` decimal(9, 2)         NOT NULL,
# 	最高价
    `upper_price` decimal(9, 2)         NOT NULL,
# 	最低价
    `lower_price` decimal(9, 2)         NOT NULL,

    PRIMARY KEY (`info_id`),
    FOREIGN KEY (`stock_id`) REFERENCES stock_basic_info (stock_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX stock_id ON stock_daily_info (stock_id);
CREATE INDEX update_date ON stock_daily_info (update_date);
CREATE UNIQUE INDEX stock_daily_record ON stock_daily_info (stock_id, update_date);
-- ----------------------------
-- Key table: fund port folio，基金持仓信息
-- 按照监管规定，每个季度进行更新
-- reference: https://waditu.com/document/2?doc_id=121
-- ----------------------------
DROP TABLE IF EXISTS `fund_portfolio`;
CREATE TABLE `fund_portfolio`
(
    `info_id`               bigint AUTO_INCREMENT NOT NULL,

#     本持仓信息的披露时间
    `announce_date`         date                  NOT NULL,
#     对应基金的代码
    `fund_code`             varchar(6)            NOT NULL,

#      股票代码
    `stock_id`              varchar(8)            NOT NULL,

    `stock_name`            varchar(255)          NOT NULL,

#     持有的股票市值
    `stock_mkv`             decimal(20, 5)        NOT NULL,

#     持有的股票数量（单位，万股）
    `stock_amount`          decimal(20, 5)        NOT NULL,

# 	占净值比例
    `percentage`            decimal(7, 2)         NOT NULL,

#     应对变化需求： 基金持仓信息状态码， 0 表示一切正常
    `fund_portfolio_status` int DEFAULT 0,

    PRIMARY KEY (`info_id`),
    FOREIGN KEY (fund_code) REFERENCES fund_basic_info (fund_code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Key table: fund message table
-- reference:
-- 说明：为了提高可扩展性而创建的，诸如基金投资策略，投资方向，
-- 基金每日公告都属于这一类信息，在具体使用时，首先要根据业务逻辑
-- 确定message类型，比如message_type = 1,表示是在基金详情页显示的
-- 字段，然后title是“投资策略”，内容是对应策略，然后前端打开详情页就
-- 搜索type = 1, fund_code = xxxxxx的基金信息，按照时间顺序
-- 创建卡片显示
-- ----------------------------
DROP TABLE IF EXISTS `fund_message`;
CREATE TABLE `fund_message`
(
#     用于基金最近重要信息的提示和更新
    `message_id`        BIGINT AUTO_INCREMENT,
    `message_timestamp` timestamp      NOT NULL,

#     message type
-- -----------------------------
-- 0: no type
-- 1: 投资目标等等description信息, 示例'https://fundf10.eastmoney.com/002106.html'
-- -----------------------------
    `message_type`      INT DEFAULT 0,

#     外键
    `fund_code`         VARCHAR(6)     NOT NULL,

    `message_title`     varchar(100)   NOT NULL,
    `message_content`   varchar(20000) NOT NULL,

    PRIMARY KEY (`message_id`),
    FOREIGN KEY (fund_code) REFERENCES fund_basic_info (fund_code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Key table: fund estimate table
-- reference:
-- 说明：基金代码，净值估算，估算增长率，时间
-- ----------------------------
DROP TABLE IF EXISTS `fund_estimate`;
CREATE TABLE `fund_estimate`
(
#     用于基金最近重要信息的提示和更新
    `estimate_id`        BIGINT AUTO_INCREMENT,
#     外键
    `fund_code`          VARCHAR(6) NOT NULL,

    `estimate_timestamp` timestamp  NOT NULL,
    `NAV_estimate`       decimal(8, 4) DEFAULT 9999.9999,
    `increase_estimate`  decimal(5, 2) DEFAULT 999.99,

    PRIMARY KEY (`estimate_id`),
    FOREIGN KEY (fund_code) REFERENCES fund_basic_info (fund_code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- ----------------------------
-- Table structure for user
-- status int = 0(normal)
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
#     unique and
    `user_id`    bigint              NOT NULL,
    `nickname`   varchar(255)        NOT NULL,

#     系统用户登录唯一凭证为邮箱，用户可以自由设定
#     可以重复的昵称
    `email`      varchar(255) UNIQUE NOT NULL,

#     应对需求变化，为0时表示用户信息一切正常
    `status`     int DEFAULT 0,

#     风险等级
    `risk_level` int DEFAULT 0 comment '范围1-5 风险等级',

#    修改字段 20210828
    `introduction` varchar(255),

#    增加字段 20210910    
    `created_at` timestamp default current_timestamp comment '创建时间',
    `updated_at` timestamp default current_timestamp comment '更新时间',

-- TODO: wait to implement: more user's info

    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# ----------------------------
# Key table: user position table， 记录用户的持仓信息
# reference:
# ----------------------------
DROP TABLE IF EXISTS `user_position`;
CREATE TABLE `user_position`
(
#     用户自己输入的持仓信息和持仓价格，存入
    `position_id`        BIGINT AUTO_INCREMENT NOT NULL,
#     外键
    `user_id`            bigint                NOT NULL,
    `fund_code`          varchar(6)            NOT NULL,
    `holding_start_date` DATE                  NOT NULL,
    `start_price`        decimal(7, 4)         NOT NULL,
    `holding_end_date`   DATE           DEFAULT '9999-12-31',
    `end_price`          decimal(7, 4)  DEFAULT 999.9999,
    `amount`             decimal(20, 5) DEFAULT 0,

    PRIMARY KEY (`position_id`),
    FOREIGN KEY (user_id) REFERENCES user (user_id),
    FOREIGN KEY (fund_code) REFERENCES fund_basic_info (fund_code)

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# TODO: 问老师是不是需要缓存表，做某些计算（如基金表现排序时，
# 是不是需要每次都排序，如要在数据库里面缓存，用sql还是别的数据库
# 主要面向：基金多时段收益率排行，基金经理多时段收益率排行
# 基金公司多时段收益率排行
# 对于机器学习模型的参数是否需要数据库

-- -----------------------------
-- Key table: fund yield rate table
-- 基金收益表，每天下午更新最新净值完成后更新，共分为：（参考支付宝）
-- 近1周，近1月，近3月，近6月，近1年，近2年，近3年，近5年，成立以来，近年来
-- 然后对每个收益率都做索引，查询时直接查询索引自动得到有序的收益率排行
-- 最后直接提取收益率序列即可
-- -----------------------------
DROP TABLE IF EXISTS `fund_yield_rate`;
CREATE TABLE `fund_yield_rate`
(
    `fund_code`             varchar(6),
    `last_update_timestamp` timestamp NOT NULL,

    `last_one_day`          decimal(7, 2) DEFAULT -1,
    `last_one_week`         decimal(7, 2) DEFAULT -1,
    `last_one_month`        decimal(7, 2) DEFAULT -1,
    `last_three_months`     decimal(7, 2) DEFAULT -1,
    `last_six_months`       decimal(7, 2) DEFAULT -1,
    `last_one_year`         decimal(7, 2) DEFAULT -1,
    `last_two_years`        decimal(7, 2) DEFAULT -1,
    `last_three_years`      decimal(7, 2) DEFAULT -1,
    `last_five_years`       decimal(7, 2) DEFAULT -1,
    `from_beginning`        decimal(7, 2) DEFAULT -1,
    `from_this_year`        decimal(7, 2) DEFAULT -1,


    PRIMARY KEY (`fund_code`),
    FOREIGN KEY (fund_code) REFERENCES fund_basic_info (fund_code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# create lots of indexes
CREATE INDEX one_day ON fund_yield_rate (last_one_day);
CREATE INDEX one_week ON fund_yield_rate (last_one_week);
CREATE INDEX one_month ON fund_yield_rate (last_one_month);
CREATE INDEX three_months ON fund_yield_rate (last_three_months);
CREATE INDEX six_months ON fund_yield_rate (last_six_months);
CREATE INDEX one_year ON fund_yield_rate (last_one_year);
CREATE INDEX two_years ON fund_yield_rate (last_two_years);
CREATE INDEX three_years ON fund_yield_rate (last_three_years);
CREATE INDEX five_years ON fund_yield_rate (last_five_years);
CREATE INDEX from_beginning ON fund_yield_rate (from_beginning);
CREATE INDEX this_year ON fund_yield_rate (from_this_year);

-- -----------------------------
-- Key table: stock yield rate table
-- -----------------------------
DROP TABLE IF EXISTS `stock_yield_rate`;
CREATE TABLE `stock_yield_rate`
(
    `stock_id`              varchar(8) NOT NULL,
    `last_update_timestamp` timestamp  NOT NULL,

    `last_one_day`          decimal(7, 2) DEFAULT -1,
    `last_one_week`         decimal(7, 2) DEFAULT -1,
    `last_one_month`        decimal(7, 2) DEFAULT -1,
    `last_three_months`     decimal(7, 2) DEFAULT -1,
    `last_six_months`       decimal(7, 2) DEFAULT -1,
    `last_one_year`         decimal(7, 2) DEFAULT -1,
    `last_two_years`        decimal(7, 2) DEFAULT -1,
    `last_three_years`      decimal(7, 2) DEFAULT -1,
    `last_five_years`       decimal(7, 2) DEFAULT -1,
    `from_beginning`        decimal(7, 2) DEFAULT -1,
    `from_this_year`        decimal(7, 2) DEFAULT -1,


    PRIMARY KEY (`stock_id`),
    FOREIGN KEY (`stock_id`) REFERENCES stock_basic_info (stock_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# create lots of indexes
CREATE INDEX one_day ON stock_yield_rate (last_one_day);
CREATE INDEX one_week ON stock_yield_rate (last_one_week);
CREATE INDEX one_month ON stock_yield_rate (last_one_month);
CREATE INDEX three_months ON stock_yield_rate (last_three_months);
CREATE INDEX six_months ON stock_yield_rate (last_six_months);
CREATE INDEX one_year ON stock_yield_rate (last_one_year);
CREATE INDEX two_years ON stock_yield_rate (last_two_years);
CREATE INDEX three_years ON stock_yield_rate (last_three_years);
CREATE INDEX five_years ON stock_yield_rate (last_five_years);
CREATE INDEX from_beginning ON stock_yield_rate (from_beginning);
CREATE INDEX this_year ON stock_yield_rate (from_this_year);

-- -----------------------------
-- Key table: fund prediction table
-- fund code - future 3 days and quote change
-- -----------------------------
DROP TABLE IF EXISTS `fund_prediction`;
CREATE TABLE `fund_prediction`
(
    `fund_code`              varchar(6),
    `fund_type`              int  not null default 0,
    `description_id`         int  not null default 6,
    `last_update_timestamp`  date NOT NULL DEFAULT '9999-12-31',

    `future_one_day_NAV`     decimal(7, 4) DEFAULT 0,
    `one_day_quote_change`   decimal(7, 2) DEFAULT -1,
    `future_two_day_NAV`     decimal(7, 4) DEFAULT 0,
    `two_day_quote_change`   decimal(7, 2) DEFAULT -1,
    `future_three_day_NAV`   decimal(7, 4) DEFAULT 0,
    `three_day_quote_change` decimal(7, 2) DEFAULT -1,

    PRIMARY KEY (`fund_code`),
    FOREIGN KEY (fund_code) REFERENCES fund_basic_info (fund_code),
    FOREIGN KEY (description_id) REFERENCES prediction_description (description_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX one_day on fund_prediction (one_day_quote_change);
CREATE INDEX two_day on fund_prediction (two_day_quote_change);
CREATE INDEX three_day on fund_prediction (three_day_quote_change);

-- -----------------------------
-- Key table: fund prediction error table
-- fund code - future 3 days and quote change
-- -----------------------------
DROP TABLE IF EXISTS `fund_prediction_error`;
CREATE TABLE `fund_prediction_error`
(
    `error_id` bigint AUTO_INCREMENT,
    `fund_code`                  varchar(6),
    `fund_type`                  int  NOT NULL  DEFAULT 0,
    `last_update_timestamp`      date NOT NULL  DEFAULT '9999-12-31',

    `today_MSE`                  decimal(12, 5) DEFAULT 0,
    `today_RMSE`                 decimal(12, 5) DEFAULT 0,
    `today_MAE`                  decimal(12, 5) DEFAULT 0,
    `today_abs_delta`            decimal(7, 2)  DEFAULT 0,
#     `yesterday_MSE`                  decimal(12, 5) DEFAULT 0,
#     `yesterday_RMSE`                 decimal(12, 5) DEFAULT 0,
#     `yesterday_MAE`                  decimal(12, 5) DEFAULT 0,
#     `yesterday_abs_delta`            decimal(7, 2)  DEFAULT 0,
#     `three_days_MSE`                  decimal(12, 5) DEFAULT 0,
#     `three_days_RMSE`                 decimal(12, 5) DEFAULT 0,
#     `three_days_MAE`                  decimal(12, 5) DEFAULT 0,
#     `three_days_abs_delta`            decimal(7, 2)  DEFAULT 0,

    PRIMARY KEY (`error_id`),
    FOREIGN KEY (fund_code) REFERENCES fund_basic_info (fund_code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX fundCode on fund_prediction_error (fund_code);
CREATE INDEX mse on fund_prediction_error (today_MSE);
CREATE INDEX rmse on fund_prediction_error (today_RMSE);
CREATE INDEX mae on fund_prediction_error (today_MAE);
CREATE INDEX abs_delta on fund_prediction_error (today_abs_delta);
# CREATE INDEX y_mse on fund_prediction_error (yesterday_MSE);
# CREATE INDEX y_rmse on fund_prediction_error (yesterday_RMSE);
# CREATE INDEX y_mae on fund_prediction_error (yesterday_MAE);
# CREATE INDEX y_abs_delta on fund_prediction_error (yesterday_abs_delta);
# CREATE INDEX t_mse on fund_prediction_error (three_days_MSE);
# CREATE INDEX t_rmse on fund_prediction_error (three_days_RMSE);
# CREATE INDEX t_mae on fund_prediction_error (three_days_MAE);
# CREATE INDEX t_abs_delta on fund_prediction_error (three_days_abs_delta);
CREATE INDEX last_update_date on fund_prediction_error (last_update_timestamp);

-- -----------------------------
-- Key table: fund competition table
-- -----------------------------
DROP TABLE IF EXISTS `fund_competition`;
CREATE TABLE `fund_competition`
(
    `competition_id`          int           NOT NULL AUTO_INCREMENT,
    `competition_name`        varchar(100)  NOT NULL DEFAULT '',
    `creator_id` bigint NOT NULL DEFAULT 1,
    `start_date`              date          NOT NULL DEFAULT '9999-12-31',
    `end_date`                date          NOT NULL DEFAULT '9999-12-31',

    `competition_description` varchar(1000) NOT NULL DEFAULT '',
#     参赛人数
    `capacity`                bigint        NOT NULL DEFAULT 100,
    `number`                bigint          NOT NULL DEFAULT 0,

    `initial_capital`         bigint        NOT NULL DEFAULT 10000,
    `allowed_fund_type`       int        NOT NULL DEFAULT 0,
    `is_public`               boolean       NOT NULL DEFAULT true,

    PRIMARY KEY (`competition_id`),
    CHECK ( number <= capacity )
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX fund_competition_name on fund_competition (competition_name);
CREATE INDEX fund_competition_start on fund_competition (`start_date`);
CREATE INDEX fund_competition_end on fund_competition (end_date);

-- -----------------------------
-- Key table: fund competition table
-- -----------------------------
DROP TABLE IF EXISTS `fund_competition_user`;
CREATE TABLE `fund_competition_user`
(
    `id`             bigint        NOT NULL AUTO_INCREMENT,
    `competition_id` int        NOT NULL,
    `user_id`        bigint     NOT NULL,

#     还剩多少现金
    `surplus_money`  decimal(15,2) NOT NULL DEFAULT 0,
#     基金资产加现金
    `total_asset`  decimal(15,2) NOT NULL DEFAULT 0,
#     初始资金
    `total_change`  decimal(15,2)  NOT NULL DEFAULT 0,
    `is_end`     boolean       NOT NULL DEFAULT true,

    PRIMARY KEY (`id`),
    foreign key (`competition_id`) references fund_competition(competition_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX user_id_competition_info on fund_competition_user (user_id);
CREATE INDEX competition_id_competition_info on fund_competition_user (competition_id);


-- -----------------------------
-- Key table: fund competition table
-- -----------------------------
DROP TABLE IF EXISTS `fund_competition_user_position`;
CREATE TABLE `fund_competition_user_position`
(
    `id`                 int           NOT NULL AUTO_INCREMENT,
    `fund_competition_user_id` bigint 		NOT NULL,
#   上一次计算参赛用户收益时间（考虑到可能会遗漏一些天数未计算）
    `calculate_date`     date          NOT NULL DEFAULT '2021-08-31',

    `fund_code`          varchar(6)    NOT NULL,
    `amount`             decimal(20, 5)         DEFAULT 0,

    PRIMARY KEY (`id`),
    foreign key (`fund_competition_user_id`) references fund_competition_user(id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
CREATE INDEX fund_competition_fund_code on fund_competition_user_position (fund_code);

-- -----------------------------
-- Key table: fund competition table
-- -----------------------------
DROP TABLE IF EXISTS `fund_competition_user_position_log`;
CREATE TABLE `fund_competition_user_position_log`
(
    `id`                       int        NOT NULL AUTO_INCREMENT,
    `fund_competition_user_id` bigint     NOT NULL,

    `fund_code`                varchar(6) NOT NULL,
    `date`                     DATE       NOT NULL,
    `amount`                   decimal(20, 5) DEFAULT 0,

    PRIMARY KEY (`id`),
    foreign key (`fund_competition_user_id`) references fund_competition_user (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
CREATE INDEX log_fund_code on fund_competition_user_position_log (fund_code);
CREATE INDEX log_fund_competition_user_id on fund_competition_user_position_log (fund_competition_user_id);


DROP TABLE IF EXISTS `prediction_description`;
CREATE TABLE `prediction_description`
(
    `description_id` int           NOT NULL AUTO_INCREMENT,
    `name`           varchar(40)   NOT NULL default '',
    `text`           varchar(1000) NOT NULL default '',

    `priority`       int           NOT NULL default 0,

    PRIMARY KEY (`description_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `prediction_description_rule`;
CREATE TABLE `prediction_description_rule`
(
    `rule_id`          int            NOT NULL AUTO_INCREMENT,
    `description_id`   int,
    `rule_type`        int            NOT NULL default 1,
#     0 >= & 1 <=
    `rule_orientation` int            NOT NULL default 0,

    `rule_value`       DECIMAL(10, 5) NOT NULL default 0.0,

    PRIMARY KEY (`rule_id`),
    foreign key (`description_id`) references fundsystem.prediction_description (description_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- -----------------------------
--  基于规则的表,记录同类均值
-- -----------------------------
DROP TABLE IF EXISTS `fund_indicator_average`;
CREATE TABLE `fund_indicator_average`
(
    `fund_type`         int         default 0  not null,
    `SHARP1_AVERAGE`    decimal(10, 4) comment '近1年夏普比率均值',
    `SHARP3_AVERAGE`    decimal(10, 4) comment '近3年夏普比率均值',
    `SHARP5_AVERAGE`    decimal(10, 4) comment '近5年夏普比率均值',
    `SHARP_1NFSC`       int comment '近1年夏普比率排名总个数',
    `SHARP_3NFSC`       int comment '近1年夏普比率排名总个数',
    `SHARP_5NFSC`       int comment '近1年夏普比率排名总个数',

    `MAXRETRA1_AVERAGE` decimal(10, 4) comment '近1年最大回撤均值',
    `MAXRETRA3_AVERAGE` decimal(10, 4) comment '近3年最大回撤均值',
    `MAXRETRA5_AVERAGE` decimal(10, 4) comment '近5年最大回撤均值',
    `MAXRETRA_1NFSC`    int comment '近1年最大回撤排名总个数',
    `MAXRETRA_3NFSC`    int comment '近3年最大回撤排名总个数',
    `MAXRETRA_5NFSC`    int comment '近5年最大回撤排名总个数',

    `STDDEV1_AVERAGE`   decimal(10, 4) comment '近1年波动率均值',
    `STDDEV3_AVERAGE`   decimal(10, 4) comment '近3年波动率均值',
    `STDDEV5_AVERAGE`   decimal(10, 4) comment '近5年波动率均值',
    `STDDEV_1NFSC`      int comment '近1年波动率排名总个数',
    `STDDEV_3NFSC`      int comment '近3年波动率排名总个数',
    `STDDEV_5NFSC`      int comment '近5年波动率排名总个数',

    `PROFIT_Y_AVERAGE` decimal(10,4)  comment '持有1个月历史盈利概率均值',
    `PROFIT_1N_AVERAGE` decimal(10,4)  comment '持有1年历史盈利概率均值',
    `PROFIT_YFSC` int comment '持有1个月历史盈利概率排名总个数',
    `PROFIT_1NFSC` int comment '持有1年历史盈利概率排名总个数',
    `created_at` timestamp default current_timestamp comment '创建时间',
    `updated_at` timestamp default current_timestamp comment '更新时间',

    `type_name` varchar(31) default '' not null comment '基金种类名称',
    `type_description` varchar(511) default '' not null comment '基金种类描述',

    PRIMARY KEY (`fund_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- -----------------------------
--  基于规则的表
-- -----------------------------
DROP TABLE IF EXISTS `fund_indicator`;
CREATE TABLE `fund_indicator`
(
    `fund_code` varchar(6) DEFAULT '000000' not null,
    `fund_type` int default 0 not null,
    `SHARP1` decimal(10,4) comment '近1年夏普比率',
    `SHARP3` decimal(10,4) comment '近3年夏普比率',
    `SHARP5` decimal(10,4) comment '近5年夏普比率',
    `SHARP_1NRANK` int comment '近1年夏普比率排名',
    `SHARP_3NRANK` int comment '近3年夏普比率排名',
    `SHARP_5NRANK` int comment '近5年夏普比率排名',

    `MAXRETRA1` decimal(10,4) comment '近1年最大回撤',
    `MAXRETRA3` decimal(10,4)  comment '近3年最大回撤',
    `MAXRETRA5` decimal(10,4)  comment '近5年最大回撤',
    `MAXRETRA_1NRANK` int comment '近1年最大回撤排名',
    `MAXRETRA_3NRANK` int comment '近3年最大回撤排名',
    `MAXRETRA_5NRANK` int comment '近5年最大回撤排名',

    `STDDEV1` decimal(10,4) comment '近1年波动率',
    `STDDEV3` decimal(10,4) comment '近3年波动率',
    `STDDEV5` decimal(10,4) comment '近5年波动率',
    `STDDEV_1NRANK` int comment '近1年波动率排名',
    `STDDEV_3NRANK` int comment '近3年波动率排名',
    `STDDEV_5NRANK` int comment '近5年波动率排名',

    `PROFIT_Y` decimal(10,4)  comment '持有1个月历史盈利概率',
    `PROFIT_1N` decimal(10,4)  comment '持有1年历史盈利概率',
    `PROFIT_YRANK` int comment '持有1个月历史盈利概率排名',
    `PROFIT_1NRANK` int comment '持有1年历史盈利概率排名',
    `created_at` timestamp default current_timestamp comment '创建时间',
    `updated_at` timestamp default current_timestamp comment '更新时间',
    PRIMARY KEY (`fund_code`)
--     FOREIGN KEY (fund_type) REFERENCES fund_indicator_average(fund_type)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- -----------------------------
--  风险评测问卷表 
-- -----------------------------  
DROP TABLE IF EXISTS `fund_risk_assessment`;
CREATE TABLE `fund_risk_assessment` 
(
    `id` int not null auto_increment comment '自增id',
    `order` int not null default 0 unique comment '题目顺序',
    `question` varchar(255) not null default '' comment '问卷题目',
    `option_1` varchar(255) comment '第一个选项',
    `option_2` varchar(255) comment '第二个选项',
    `option_3` varchar(255) comment '第三个选项',
    `option_4` varchar(255) comment '第四个选项',
    `option_5` varchar(255) comment '第五个选项',
    `score_1` int unsigned comment '第一个选项对应的分数',
    `score_2` int unsigned comment '第二个选项对应的分数',
    `score_3` int unsigned comment '第三个选项对应的分数',
    `score_4` int unsigned comment '第四个选项对应的分数',
    `score_5` int unsigned comment '第五个选项对应的分数',
    `created_at` timestamp default current_timestamp comment '创建时间',
    `updated_at` timestamp default current_timestamp comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


  -- -----------------------------
-- Key table: no model fund and pointer
-- fund type - fund code
-- -----------------------------
DROP TABLE IF EXISTS `fund_model`;
CREATE TABLE `fund_model`
(
    `fund_type` int        DEFAULT 0,
    `fund_code` varchar(6) DEFAULT '000000',

    PRIMARY KEY (`fund_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;



-- ----------------------------
-- ATTENTION: 以下的信息是作为备份，不需要作为sql脚本真实被执行
-- 执行方法可以参见spring security file
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
# DROP TABLE IF EXISTS `role`;
# CREATE TABLE `role`
# (
# #     unique and
#     `role_id`     int    AUTO_INCREMENT      NOT NULL,
#     `role_nameEN` varchar(255) NOT NULL,
#     `role_nameZH` varchar(255) NOT NULL,
#     PRIMARY KEY (`role_id`)
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for user_auth
-- Important: user identity
-- ----------------------------
# DROP TABLE IF EXISTS `user_auth`;
# CREATE TABLE `user_auth`
# (
#     `user_id`                bigint AUTO_INCREMENT NOT NULL,
#     `username`              varchar(255) UNIQUE   NOT NULL,
#     `password`              varchar(255)          NOT NULL,
#
# #     一些必须带上，继承自userDetails的字段名，套用Spring security的
# #     框架可以实现加密登录
# #     账户是不是过期了
#     `account_non_expired`     boolean DEFAULT true,
# #     账户是否被锁定
#     `account_non_locked`      boolean DEFAULT true,
# #     登陆凭据是否过期了
#     `credentials_non_expired` boolean DEFAULT true,
# #     账户是否可用
#     `enabled`               boolean DEFAULT true,
#
#     PRIMARY KEY (`user_id`)
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = utf8;

-- ----------------------------
-- Key table: user auth & role的关联表，主要用于spring security的框架
-- ----------------------------
# DROP TABLE IF EXISTS `user_auth_roles`;
# DROP TABLE IF EXISTS `user_auth_role`;
# CREATE TABLE `user_auth_roles`
# (
#     `id`                bigint AUTO_INCREMENT NOT NULL,
#     `user_auth_user_id`              bigint  NOT NULL,
#     `roles_role_id`              int     NOT NULL,
#
#     PRIMARY KEY (`id`),
#     FOREIGN KEY (`roles_role_id`) REFERENCES role(`role_id`),
#     FOREIGN KEY (`user_auth_user_id`) REFERENCES user_auth(user_id)
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = utf8;


# ----------------------------
# Basic info for System: user Auth and user info
# ----------------------------
#
# ----------------------------
# Records of role
# User type: 0 super admin, 1 common admin, 2 super user, 3 common user
# ----------------------------

# INSERT INTO `role` (role_id, role_nameEN, role_nameZH)
# VALUES (1, 'superAdmin', '系统管理员');
#
# INSERT INTO `role` (role_id, role_nameEN, role_nameZH)
# VALUES (2, 'admin', '管理员');
#
# INSERT INTO `role` (role_id, role_nameEN, role_nameZH)
# VALUES (3, 'superUser', '超级会员');
#
# INSERT INTO `role` (role_id, role_nameEN, role_nameZH)
# VALUES (4, 'user', '普通会员');

-- ----------------------------
-- Records of user_auth
-- User type: 0 super admin, 1 common admin, 2 super user, 3 common user
-- ----------------------------

# INSERT INTO `user_auth` (user_id, username, password)
# VALUES (1, 'superAdmin', '123456');
# INSERT INTO `user_auth_roles` (user_auth_user_id, roles_role_id)
# VALUES ('1', '0');
#
# INSERT INTO `user_auth` (user_id, username, password)
# VALUES (2, 'admin', '123456');
# INSERT INTO `user_auth_roles` (user_auth_user_id, roles_role_id)
# VALUES ('2', '1');
#
#
# INSERT INTO `user_auth` (user_id, username, password)
# VALUES (3, 'superUser', '123456');
# INSERT INTO `user_auth_roles` (user_auth_user_id, roles_role_id)
# VALUES ('3', '2');
#
# INSERT INTO `user_auth` (user_id, username, password)
# VALUES (4, 'user', '123456');
# INSERT INTO `user_auth_roles` (user_auth_user_id, roles_role_id)
# VALUES ('4', '3');
