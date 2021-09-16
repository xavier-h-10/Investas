package com.fundgroup.backend.ControllerTest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.cache.StockCache;
import com.fundgroup.backend.constant.Constant;
import com.fundgroup.backend.controller.schedule.CustomSchedule;
import com.fundgroup.backend.service.CrawlerService;
import com.fundgroup.backend.service.FundDailyInfoService;
import com.fundgroup.backend.service.FundIndicatorService;
import com.fundgroup.backend.utils.CustomTaskScheduler;
import com.fundgroup.backend.utils.messageUtils.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomScheduleTest {

    @Autowired
    private CustomSchedule customSchedule;

    @Test
    public void testStartAndCancelJob()
    {
        String json="{ \"scheduleType\":6, \"arguments\":[{\"name\":\"project\",\"value\":\"fund\"}],\"cronStrings\":[\"0 * * * * 1-6\"]}";
        Message msg=customSchedule.startScheduleWrapper(json);
        JSONArray jsonArray=msg.getData().getJSONArray("uuid");
        String uuid= (String) jsonArray.get(0);

        String cancelJson=String.format("{\"job\":[\"%s\"]}",uuid);
        customSchedule.cancelScheduleWrapper(cancelJson);

    }

    @Test
    public void testGetAllScheduleStatus()
    {
        customSchedule.getAllScheduleStatusWrapper();
    }


}
