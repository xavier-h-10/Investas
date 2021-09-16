package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.constant.Constant;
import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundDailyInfo;
import com.fundgroup.backend.serviceImpl.CrawlerServiceImpl;
import com.fundgroup.backend.serviceImpl.FundBasicInfoServiceImpl;
import com.fundgroup.backend.utils.messageUtils.Message;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class CrawlerServiceTest {

    @Mock
    private FundBasicInfoDao fundBasicInfoDao;

    @Mock
    private FundDailyInfoDao fundDailyInfoDao;

    @InjectMocks
    private CrawlerServiceImpl crawlerService;

    @Before
    public void setUp()
    {
        LocalDate today=LocalDate.now();
        MockitoAnnotations.initMocks(this);
        Mockito.when(fundBasicInfoDao.getFundNumber()).thenReturn(10);
        Mockito.when(fundDailyInfoDao.getDailyInfoCountByUpdateDate(today)).thenReturn(10);
    }

    @Test
    public void startAndCancelSpider()
    {
        HashMap<String,String> test=new HashMap<>();
        test.put(Constant.STR_SPIDER, "test");
        Message msg = crawlerService.startSpider(test);
        String jobid= (String) msg.getData().get("jobid");
        HashMap<String,String> cancel=new HashMap<>();
        cancel.put("job", jobid);
        Message msg2 =crawlerService.cancelSpider(cancel);
    }

    @Test
    public void listJobs()
    {
        Message msg = crawlerService.listJobs();
        assertNotNull(msg.getStatus());
    }

    @Test
    public void startRunnable()
    {
        HashMap<String,String> test=new HashMap<>();
        test.put(Constant.STR_SPIDER, "test");
        crawlerService.startSpiderRunnable(test).run();
    }

    @Test
    public void tryCrawlerTodayNetValue()
    {
        crawlerService.tryCrawlerTodayNetValue(1,1);
    }


}
