package com.fundgroup.backend.CacheTest;

import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.cache.StockCache;
import com.fundgroup.backend.dao.StockBasicInfoDao;
import com.fundgroup.backend.dao.StockDailyInfoDao;
import com.fundgroup.backend.dto.StockDailySimp;
import com.fundgroup.backend.entity.StockBasicInfo;
import com.fundgroup.backend.service.StockBasicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StockCacheTest {
    @Mock
    private StockBasicInfoDao stockBasicInfoDao;

    @Mock
    private StockBasicService stockBasicService;

    @Mock
    private StockDailyInfoDao stockDailyInfoDao;

    @InjectMocks
    private StockCache stockCache;

    @Before
    public void setUp()
    {
        List<StockDailySimp> stockDailySimps=new ArrayList<>();
        stockDailySimps.add(new StockDailySimp());
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("value",1);
        Mockito.when(stockBasicInfoDao.getStockBasicInfoByStockId("000300")).thenReturn(new StockBasicInfo());
        Mockito.when(stockDailyInfoDao.getAllByStockId("000300")).thenReturn(stockDailySimps);
        Mockito.when(stockBasicService.getStockInfoById("000300")).thenReturn(jsonObject);
    }

    @Test
    public void loadAndTest()
    {
        stockCache.loadData();
        stockCache.getStockInfoById("000300");
        stockCache.getDailyInfoById("000300");
        stockCache.updateBasicInfoCache();
        stockCache.updateBasicInfoCacheByStockId("000300");
    }

}
