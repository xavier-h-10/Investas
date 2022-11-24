package com.fundgroup.backend.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.constant.Constant;
import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.service.CrawlerService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import com.fundgroup.backend.utils.spiderUtils.SpiderUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/*
start spider
example response
{"status": "ok", "jobid": "6487ec79947edab326d6db28a2d86511e8247444"}
*/

@Service
@Log4j2
public class CrawlerServiceImpl implements CrawlerService{
    SpiderUtils spiderUtils=new SpiderUtils();

    @Autowired
    private FundBasicInfoDao fundBasicInfoDao;

    @Autowired
    private FundDailyInfoDao fundDailyInfoDao;

    @Override
    public Message startSpider(HashMap<String,String> spiderArgument)
    {
        String spider_name= spiderArgument.get(Constant.STR_SPIDER);
        String mode= spiderArgument.get(Constant.STR_MODE);
        String fetch_magic=spiderArgument.get(Constant.STR_FETCH_MAGIC);
        String fund_code=spiderArgument.get(Constant.STR_FUND_CODE);
        String cur_time=null;


        if(spider_name.equals(Constant.SPR_JZGS))
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            cur_time = LocalDateTime.now().format(formatter);
        }

        ResponseEntity<String> response=spiderUtils.postRequest(Constant.SPR_SCHEDULE_URL,spider_name,mode,fetch_magic,fund_code,cur_time,null);
        if(response==null)
        {
            return new Message(MessageUtil.ERROR);
        }
        HttpStatus statusCode = response.getStatusCode();
        String body= response.getBody();
        System.out.println("start spider:"+statusCode.value()+" "+body);
        return new Message(statusCode.value(),body);
    }

    @Override
    public Message listJobs()
    {
        ResponseEntity<String> response=spiderUtils.getResponse(Constant.SPR_LIST_JOBS_URL);
        if(response==null)
        {
            return new Message(MessageUtil.ERROR);
        }
        HttpStatus statusCode = response.getStatusCode();
        String body= response.getBody();
        System.out.println("list jobs:"+statusCode.value()+" "+body);

        JSONObject jsonObject = JSONObject.parseObject(body);
        return new Message(statusCode.value(),jsonObject);
    }

    @Override
    public Message cancelSpider(HashMap<String,String> spiderArgument)
    {
        String job= spiderArgument.get(Constant.STR_JOB);
        ResponseEntity<String> response=spiderUtils.postRequest(Constant.SPR_CANCEL_URL,null,null,null,null,null,job);
        if(response==null)
        {
            return new Message(MessageUtil.ERROR);
        }
        HttpStatus statusCode = response.getStatusCode();
        String body= response.getBody();
        System.out.println("cancel spider:"+statusCode.value()+" "+body);

        JSONObject jsonObject = JSONObject.parseObject(body);
        return new Message(statusCode.value(),null,jsonObject);
    }

    /**
     * test method TODO:delete
     * @param spiderArgument
     * @return
     */
    @Override
    public Runnable startSpiderRunnable(HashMap<String,String> spiderArgument)
    {
        return ()->{
            startSpider(spiderArgument);
        };
    }

    /**
     *
     * @param maxTry
     * @param intervalMinute
     * @return update fund number of today info
     */
    @Override
    public Integer tryCrawlerTodayNetValue(Integer maxTry,Integer intervalMinute) {
        Integer num=-1;
        LocalDate today=LocalDate.now();
        while(maxTry!=0)
        {
            Message message=startSpider(Constant.HISTORIC_NET_LATEST_ARGS);
            try {
                TimeUnit.MINUTES.sleep(intervalMinute);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Integer todayUpdateNum=fundDailyInfoDao.getDailyInfoCountByUpdateDate(today);
            Integer allFundNum=fundBasicInfoDao.getFundNumber();
            if(todayUpdateNum>=(allFundNum*0.8))
            {
                num=todayUpdateNum;
                break;
            }

            --maxTry;
        }
        return num;
    }
}
