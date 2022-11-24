package com.fundgroup.backend.controller.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.cache.StockCache;
import com.fundgroup.backend.constant.*;
import com.fundgroup.backend.dto.ScheduleTaskInfo;
import com.fundgroup.backend.service.CrawlerService;
import com.fundgroup.backend.service.FundDailyInfoService;
import com.fundgroup.backend.service.FundIndicatorService;
import com.fundgroup.backend.utils.CustomTaskScheduler;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.*;


@RestController
@Log4j2
public class CustomSchedule {
    @Autowired
    @Qualifier("customTaskScheduler")
    private CustomTaskScheduler customTaskScheduler;

    @Autowired
    private StockCache stockCache;

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    private FundIndicatorService fundIndicatorService;

    @Autowired
    private FundDailyInfoService fundDailyInfoService;

    private JSONArray startScheduleService(ScheduleType scheduleType, HashMap<String,String> arguments, List<CronTrigger> cronTriggerList)
    {
        JSONArray jsonArray=new JSONArray();
        Runnable serviceRunnable = null;
        switch(scheduleType)
        {
            case SpiderStart:
                serviceRunnable=crawlerService.startSpiderRunnable(arguments);
                break;
            case StockIndexUpdate:
                serviceRunnable=()->stockCache.updateBasicInfoCacheByStockId(arguments.get("stockCode"));
                break;
            case StockAllUpdate:
                serviceRunnable=()->stockCache.updateBasicInfoCache();
                break;
            case FundIndicatorUpdate:
                serviceRunnable=fundIndicatorService.wrappedUpdateService();
                break;
            case FundDailyInfoInterpolation:
                serviceRunnable=()->fundDailyInfoService.missingDateInterpolation();
                break;
            default:
                return null;
        }

        for(CronTrigger cronTrigger:cronTriggerList)
        {
            System.out.println(cronTrigger.getExpression());//test
            String uuid= UUID.randomUUID().toString().replace("-", "");
            customTaskScheduler.schedule(uuid, scheduleType, cronTrigger.getExpression(), serviceRunnable, cronTrigger);
            log.info("add custom schedule:"+uuid+" "+scheduleType+" "+cronTrigger.getExpression());
            jsonArray.add(uuid);
        }
        return jsonArray;
    }

    /**
     * TODO:make anyone to admin
     * @param scheduleRequest
     * @return
     * scheduleRequest JSON,arguments must be covered in string
     * {
     *     "scheduleType":1,
     *     "arguments":[{"name":"project","value":"fund"},{"name":...,"value":...}],
     *     "cronStrings":["0 * * * * 1-6","0 * * * * 2-6"]
     * }
     */
    @RequestMapping(value = "/anyone/schedule/custom/start",method = RequestMethod.POST)
    public Message startScheduleWrapper(@RequestBody String scheduleRequest)
    {
        JSONObject scheduleJson=JSONObject.parseObject(scheduleRequest);
        Integer scheduleTypeInt=scheduleJson.getInteger("scheduleType");
        JSONArray cronStringsArray=scheduleJson.getJSONArray("cronStrings");
        JSONArray argumentsArray=scheduleJson.getJSONArray("arguments");

        ScheduleType scheduleType=ScheduleType.values()[scheduleTypeInt];

        Object[] cronStrings=cronStringsArray.toArray();
        List<CronTrigger> cronTriggerList=new ArrayList<>();
        for(Object cronString:cronStrings)
        {
            CronTrigger cronTrigger=new CronTrigger(cronString.toString());
            cronTriggerList.add(cronTrigger);
        }

        HashMap<String,String> arguments=new HashMap<>();

        if(!argumentsArray.isEmpty())
        {
            int len=argumentsArray.size();
            for (int i=0;i<len;++i) {
                JSONObject object=argumentsArray.getJSONObject(i);
                String key= (String) object.get("name");
                String value= (String) object.get("value");
                arguments.put(key,value);
            }
        }

        JSONArray jsonArray = startScheduleService(scheduleType,arguments,cronTriggerList);

        if(jsonArray==null)
        {
            return new Message(MessageUtil.ERROR);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("uuid",jsonArray);
        return new Message(MessageUtil.SUCCESS,jsonObject);
    }

    /**
     *
     * @param cancelRequest
     * @return
     * request json
     *
     */
    @RequestMapping(value = "/anyone/schedule/custom/cancel",method = RequestMethod.POST)
    public Message cancelScheduleWrapper(@RequestBody String cancelRequest)
    {
        JSONObject cancelObject=JSONObject.parseObject(cancelRequest);
        JSONArray jobArray=cancelObject.getJSONArray("job");
        Object[] jobObjArray= jobArray.toArray();
        JSONArray cancelStatusArray=new JSONArray();
        for(Object job:jobObjArray)
        {
            JSONObject jsonObject=new JSONObject();
            ScheduleStatus scheduleStatus = customTaskScheduler.cancelSchedule(job.toString());
            jsonObject.put("jobId",job.toString());
            jsonObject.put("status",scheduleStatus.ordinal());
            cancelStatusArray.add(jsonObject);
        }
        JSONObject result=new JSONObject();
        result.put("job",cancelStatusArray);
        return new Message(MessageUtil.SUCCESS,result);

    }

    @RequestMapping(value = "/anyone/schedule/custom/status",method = RequestMethod.GET)
    public Message getAllScheduleStatusWrapper()
    {
        List<ScheduleTaskInfo> scheduleTaskInfos = customTaskScheduler.getAllJobStatus();
        JSONObject result=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        for(ScheduleTaskInfo scheduleTaskInfo:scheduleTaskInfos)
        {
            JSONObject jsonObject= (JSONObject) JSON.toJSON(scheduleTaskInfo);
            jsonArray.add(jsonObject);
        }
        result.put("job",jsonArray);
        return new Message(MessageUtil.SUCCESS,result);
    }


    @PostConstruct
    public void scheduleDefault() {
        startScheduleService(ScheduleType.StockIndexUpdate, Constant.STOCK_UPDATE_ARGS,Cron.TRAN_ONE_MINUTE);
        startScheduleService(ScheduleType.SpiderStart, Constant.JZGS_ARGS,Cron.TRAN_FIVE_MINUTE);//cur time load in service
        startScheduleService(ScheduleType.StockAllUpdate,null,Cron.TRAN_FIVE_MINUTE);//TODO:test
        startScheduleService(ScheduleType.FundIndicatorUpdate,null,Cron.CRON_PM1700);
        startScheduleService(ScheduleType.SpiderStart,Constant.STOCK_DAILY_000300_ARGS,Cron.CRON_PM1600);
    }
}
