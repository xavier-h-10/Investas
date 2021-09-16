package com.fundgroup.backend.ControllerTest;

import com.alibaba.fastjson.JSONArray;
import com.fundgroup.backend.cache.FundAssemblyCache;
import com.fundgroup.backend.controller.schedule.PipelineSchedule;
import com.fundgroup.backend.service.CrawlerService;
import com.fundgroup.backend.service.FundCompeUserService;
import com.fundgroup.backend.service.FundRateService;
import com.fundgroup.backend.utils.messageUtils.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PipelineScheduleTest {

    @Mock
    private CrawlerService crawlerService;

    @Mock
    private FundAssemblyCache fundAssemblyCache;

    @Mock
    private FundRateService fundRateService;

    @Mock
    private FundCompeUserService fundCompeUserService;

    @Autowired
    private PipelineSchedule pipelineSchedule;

    @InjectMocks
    private PipelineSchedule mockPipeline;

    @Before
    public void setUp()
    {

    }

    @Test
    public void testStartAndCancelJob()
    {
        String json="{ \"scheduleType\":6, \"arguments\":[{\"name\":\"project\",\"value\":\"fund\"}],\"cronStrings\":[\"0 * * * * 1-6\"]}";
        Message msg=pipelineSchedule.startScheduleWrapper(json);
        JSONArray jsonArray=msg.getData().getJSONArray("uuid");
        String uuid= (String) jsonArray.get(0);

        String cancelJson=String.format("{\"job\":[\"%s\"]}",uuid);
        pipelineSchedule.cancelScheduleWrapper(cancelJson);

        String json0="{ \"scheduleType\":0, \"arguments\":[{\"name\":\"project\",\"value\":\"fund\"}],\"cronStrings\":[\"0 * * * * 1-6\"]}";
        pipelineSchedule.startScheduleWrapper(json0);
        String json1="{ \"scheduleType\":1, \"arguments\":[{\"name\":\"project\",\"value\":\"fund\"}],\"cronStrings\":[\"0 * * * * 1-6\"]}";
        pipelineSchedule.startScheduleWrapper(json1);
        String json2="{ \"scheduleType\":2, \"arguments\":[{\"name\":\"project\",\"value\":\"fund\"}],\"cronStrings\":[\"0 * * * * 1-6\"]}";
        pipelineSchedule.startScheduleWrapper(json2);
        String json3="{ \"scheduleType\":3, \"arguments\":[{\"name\":\"project\",\"value\":\"fund\"}],\"cronStrings\":[\"0 * * * * 1-6\"]}";
        pipelineSchedule.startScheduleWrapper(json3);
        String json4="{ \"scheduleType\":4, \"arguments\":[{\"name\":\"project\",\"value\":\"fund\"}],\"cronStrings\":[\"0 * * * * 1-6\"]}";
        pipelineSchedule.startScheduleWrapper(json4);
        String json5="{ \"scheduleType\":5, \"arguments\":[{\"name\":\"project\",\"value\":\"fund\"}],\"cronStrings\":[\"0 * * * * 1-6\"]}";
        pipelineSchedule.startScheduleWrapper(json5);
        String json6="{ \"scheduleType\":6, \"arguments\":[{\"name\":\"project\",\"value\":\"fund\"}],\"cronStrings\":[\"0 * * * * 1-6\"]}";
        pipelineSchedule.startScheduleWrapper(json6);
        String json7="{ \"scheduleType\":7, \"arguments\":[{\"name\":\"project\",\"value\":\"fund\"}],\"cronStrings\":[\"0 * * * * 1-6\"]}";
        pipelineSchedule.startScheduleWrapper(json7);

    }

    @Test
    public void testGetAllScheduleStatus()
    {
        pipelineSchedule.getAllScheduleStatusWrapper();
    }

    @Test
    public void dailySchedule()
    {
        List<Integer> integers=new ArrayList<>();
        integers.add(1);
        try{
            Mockito.when(crawlerService.tryCrawlerTodayNetValue(70,5)).thenReturn(10);
            Mockito.doNothing().when(fundAssemblyCache).updateFundDailyInfo();
            Mockito.when(fundRateService.updateFundRate(LocalDate.now())).thenReturn(true);
            Mockito.doNothing().when(fundAssemblyCache).updateFundRank();
            Mockito.doNothing().when(fundAssemblyCache).updateFundRate();
            Mockito.when(fundCompeUserService.updateActiveCompetition()).thenReturn(integers);
            mockPipeline.dailySchedule();
        }catch(InterruptedException e){}

    }

    @Test
    public void dailySchedule2()
    {
        List<Integer> integers=new ArrayList<>();
        integers.add(1);
        try{
            Mockito.when(crawlerService.tryCrawlerTodayNetValue(70,5)).thenReturn(-1);
            mockPipeline.dailySchedule();
        }catch(InterruptedException e){}

    }

    @Test
    public void dailySchedule3()
    {
        List<Integer> integers=new ArrayList<>();
        integers.add(1);
        try{
            Mockito.when(crawlerService.tryCrawlerTodayNetValue(70,5)).thenReturn(10);
            Mockito.doNothing().when(fundAssemblyCache).updateFundDailyInfo();
            Mockito.when(fundRateService.updateFundRate(LocalDate.now())).thenReturn(false);
            mockPipeline.dailySchedule();
        }catch(InterruptedException e){}

    }
}
