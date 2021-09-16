package com.fundgroup.backend.constant;

import java.time.LocalDate;
import java.util.HashMap;

public class Constant {
	public static final String LOGIN_SUCCESS = "登录成功";

	public static final String FRONTEND_URL = "localhost:3000";

	//spider constant
	public static final String SPR_PROJECT_NAME = "fund";
	public static final String SPR_HISTORIC_NET = "netvalue";
	public static final String SPR_COMPANY = "company";
	public static final String SPR_STOCK_HOLD = "stockhold";
	public static final String SPR_MANAGER_INFO = "manager_info";
	public static final String SPR_FUND_INFO = "fund_info";
	public static final String SPR_STOCK_INDEX = "stock_index";
	public static final String SPR_JZGS = "jzgs";

	public static final String STR_PROJECT = "project";
	public static final String STR_SPIDER = "spider";
	public static final String STR_JOB = "job";
	public static final String STR_MODE = "mode";
	public static final String STR_FUND_CODE = "fundcode";
	public static final String STR_FETCH_MAGIC = "fetchmagic";
	public static final String STR_CUR_TIME = "cur_time";

	public static final String SPR_DAEMON_STATUS_URL = "http://localhost:6800/daemonstatus.json";//GET
	public static final String SPR_ADD_VERSION_URL = "http://localhost:6800/addversion.json";//POST
	public static final String SPR_SCHEDULE_URL = "http://localhost:6800/schedule.json";//POST
	public static final String SPR_CANCEL_URL = "http://localhost:6800/cancel.json";//POST
	public static final String SPR_LIST_SPIDERS_URL = "http://localhost:6800/listspiders.json";//GET
	public static final String SPR_LIST_JOBS_URL = "http://localhost:6800/listjobs.json?project=" + SPR_PROJECT_NAME;//GET

	public static final HashMap<String, String> STOCK_INDEX_ARGS = new HashMap<String, String>() {{
		put(STR_PROJECT, SPR_PROJECT_NAME);
		put(STR_SPIDER, SPR_STOCK_INDEX);
	}};

	public static final HashMap<String, String> JZGS_ARGS = new HashMap<String, String>() {{
		put(STR_PROJECT, SPR_PROJECT_NAME);
		put(STR_SPIDER, SPR_JZGS);
	}};

	public static final HashMap<String, String> HISTORIC_NET_LATEST_ARGS = new HashMap<String, String>() {{
		put(STR_PROJECT, SPR_PROJECT_NAME);
		put(STR_SPIDER, SPR_HISTORIC_NET);
		put(STR_MODE, "0");
		put(STR_FETCH_MAGIC, "1");
	}};

	public static final HashMap<String, String> STOCK_DAILY_000300_ARGS = new HashMap<String, String>() {{
		put(STR_PROJECT, SPR_PROJECT_NAME);
		put(STR_SPIDER, "stock_historic");
		put(STR_MODE, "1");
		put("secid", "1.000300");
		put("begin_date", LocalDate.now().toString().replace("-", ""));
		put("end_date", LocalDate.now().toString().replace("-", ""));
	}};

	public static final HashMap<String, String> STOCK_DAILY_HISTORIC_000300_ARGS = new HashMap<String, String>() {{
		put(STR_PROJECT, SPR_PROJECT_NAME);
		put(STR_SPIDER, "stock_historic");
		put(STR_MODE, "0");
		put("secid", "1.000300");
	}};

	public static final HashMap<String, String> STOCK_UPDATE_ARGS = new HashMap<String, String>() {{
		put("stockCode", "000300");
	}};

}
