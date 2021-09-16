package com.fundgroup.backend.controller.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.cache.FundAssemblyCache;
import com.fundgroup.backend.cache.StockCache;
import com.fundgroup.backend.constant.*;
import com.fundgroup.backend.dto.FundAssembly;
import com.fundgroup.backend.dto.ScheduleTaskInfo;
import com.fundgroup.backend.entity.FundPredictionError;
import com.fundgroup.backend.service.*;
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
import java.time.LocalDate;
import java.util.*;

@RestController
@Log4j2
public class PipelineSchedule {

    @Autowired
    @Qualifier("pipelineTaskScheduler")
    private CustomTaskScheduler pipelineTaskScheduler;

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    private FundRateService fundRateService;

    @Autowired
    private FundCompeUserService fundCompeUserService;

    @Autowired
    private FundIndicatorService fundIndicatorService;

    @Autowired
    private StockCache stockCache;

    @Autowired
    private FundAssemblyCache fundAssemblyCache;

    @Autowired
    private TensorflowService tensorflowService;

    @Autowired
    private FundDailyInfoService fundDailyInfoService;


    public void dailySchedule() throws InterruptedException {
        LocalDate today=LocalDate.now();
        //crawler daily net value
        Integer todayUpdateNum=crawlerService.tryCrawlerTodayNetValue(70,5);
        if(todayUpdateNum<0)
        {
            //error
            log.error("fail to crawl today fund net value");
            return;
        }
        fundAssemblyCache.updateFundDailyInfo();
        //calculate daily fund rate
        boolean updateRateSuccess=fundRateService.updateFundRate(today);
        if(!updateRateSuccess)
        {
            //error
            log.error("fail to calculate today fund yield rate");
            return;
        }
        fundAssemblyCache.updateFundRate();
        fundAssemblyCache.updateFundRank();
        //update competition information
        List<Integer> failCompetition = fundCompeUserService.updateActiveCompetition();
        if(!failCompetition.isEmpty())
        {
            StringBuilder fail = new StringBuilder();
            for(Integer id:failCompetition)
            {
                fail.append(id.toString()).append(" ");
            }
            log.error("competition fail to update:"+fail.toString());
        }
    }


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
            case pipelineDailyDefault:
                serviceRunnable=()->{
                    try {
                        dailySchedule();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
                break;

            default:
                return null;
        }

        for(CronTrigger cronTrigger:cronTriggerList)
        {
            String uuid= UUID.randomUUID().toString().replace("-", "");
            pipelineTaskScheduler.schedule(uuid, scheduleType, cronTrigger.getExpression(), serviceRunnable, cronTrigger);
            log.info("add pipeline schedule:"+uuid+" "+scheduleType+" "+cronTrigger.getExpression());
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
    @RequestMapping(value = "/anyone/schedule/pipeline/start",method = RequestMethod.POST)
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

    @RequestMapping(value = "/anyone/schedule/pipeline/cancel",method = RequestMethod.POST)
    public Message cancelScheduleWrapper(@RequestBody String cancelRequest)
    {
        JSONObject cancelObject=JSONObject.parseObject(cancelRequest);
        JSONArray jobArray=cancelObject.getJSONArray("job");
        Object[] jobObjArray= jobArray.toArray();
        JSONArray cancelStatusArray=new JSONArray();
        for(Object job:jobObjArray)
        {
            JSONObject jsonObject=new JSONObject();
            ScheduleStatus scheduleStatus = pipelineTaskScheduler.cancelSchedule(job.toString());
            jsonObject.put("jobId",job.toString());
            jsonObject.put("status",scheduleStatus.ordinal());
            cancelStatusArray.add(jsonObject);
        }
        JSONObject result=new JSONObject();
        result.put("job",cancelStatusArray);
        return new Message(MessageUtil.SUCCESS,result);

    }

    @RequestMapping(value = "/anyone/schedule/pipeline/status",method = RequestMethod.GET)
    public Message getAllScheduleStatusWrapper()
    {
        List<ScheduleTaskInfo> scheduleTaskInfos = pipelineTaskScheduler.getAllJobStatus();
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



    /**
    * default schedule when start spring boot
    */
    @PostConstruct
    public void scheduleDefault()
    {
        String dailyScheduleUuid= UUID.randomUUID().toString().replace("-", "");
        String predUpdateScheduleUuid= UUID.randomUUID().toString().replace("-", "");
        String tfCalculateError=UUID.randomUUID().toString().replace("-", "");
        String tfUpdate=UUID.randomUUID().toString().replace("-", "");

        pipelineTaskScheduler.schedule(dailyScheduleUuid, ScheduleType.pipelineDailyDefault,Cron.PM2100,()-> {
            try {
                log.info("start pipelineDailyDefault");
                dailySchedule();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },new CronTrigger(Cron.PM2100));

//        pipelineTaskScheduler.schedule(()->fundIndicatorService.wrappedUpdateService(),new CronTrigger(Cron.PM1700));
    pipelineTaskScheduler.schedule(()-> tensorflowService.calculateError(), new CronTrigger(Cron.AM0000));
    pipelineTaskScheduler.schedule(()->tensorflowService.update(),new CronTrigger(Cron.AM0200));

        //        pipelineTaskScheduler.schedule(()->fundIndicatorService.wrappedUpdateService(),new CronTrigger(Cron.PM1700));
        pipelineTaskScheduler.schedule(predUpdateScheduleUuid,ScheduleType.PredictionUpdate,Cron.AM0500,()->fundAssemblyCache.updateFundPrediction(),new CronTrigger(Cron.AM0500));

    }

}
