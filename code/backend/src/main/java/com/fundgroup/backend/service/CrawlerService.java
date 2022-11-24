package com.fundgroup.backend.service;

import com.fundgroup.backend.utils.messageUtils.Message;

import java.util.HashMap;

public interface CrawlerService {
    Message startSpider(HashMap<String,String> spiderArgument);
    Message listJobs();
    Message cancelSpider(HashMap<String,String> spiderArgument);
    Runnable startSpiderRunnable(HashMap<String,String> spiderArgument);
    Integer tryCrawlerTodayNetValue(Integer maxTry,Integer intervalMinute) throws InterruptedException;
}
