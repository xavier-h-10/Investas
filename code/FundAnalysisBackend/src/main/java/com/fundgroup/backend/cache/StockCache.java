package com.fundgroup.backend.cache;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dao.StockBasicInfoDao;
import com.fundgroup.backend.dao.StockDailyInfoDao;
import com.fundgroup.backend.dto.StockDailySimp;
import com.fundgroup.backend.entity.StockBasicInfo;
import com.fundgroup.backend.service.StockBasicService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockCache {
    private HashMap<String, StockBasicInfo> basicInfoHashMap =new HashMap<>();
    private HashMap<String, List<StockDailySimp>> dailyInfoHashMap =new HashMap<>();

    @Autowired
    private StockBasicInfoDao stockBasicInfoDao;

    @Autowired
    private StockBasicService stockBasicService;

    @Autowired
    private StockDailyInfoDao stockDailyInfoDao;

    public StockCache(){}

    @PostConstruct
    public void loadData()
    {
        StockBasicInfo stockBasicInfo=stockBasicInfoDao.getStockBasicInfoByStockId("000300");
        if(stockBasicInfo!=null)
        {
            basicInfoHashMap.put(stockBasicInfo.getStockId(),stockBasicInfo);
        }

        List<StockDailySimp> stockDailySimps=stockDailyInfoDao.getAllByStockId("000300");
        if(!stockDailySimps.isEmpty())
        {
            dailyInfoHashMap.put("000300",stockDailySimps);
        }
    }

    public StockBasicInfo getStockInfoById(String stockId)
    {
        StockBasicInfo stockBasicInfo= basicInfoHashMap.get(stockId);
        if(stockBasicInfo!=null) return stockBasicInfo;
        JSONObject stockDetail=stockBasicService.getStockInfoById(stockId);
        if(stockDetail==null)
            return null;
        Double value= Double.parseDouble(stockDetail.get("value").toString());
        StockBasicInfo addInfo=new StockBasicInfo(stockId,value);
        basicInfoHashMap.put(stockId,addInfo);
        return addInfo;
    }

    public List<StockDailySimp> getDailyInfoById(String stockId)
    {
        if(!dailyInfoHashMap.containsKey(stockId))
            return null;
        return dailyInfoHashMap.get(stockId);
    }

    public void updateBasicInfoCache()
    {
        System.out.println("update all stock cache");
        for(Map.Entry<String,StockBasicInfo> entry: basicInfoHashMap.entrySet())
        {
            String stockId=entry.getKey();
            StockBasicInfo entryValue=entry.getValue();
            JSONObject stockDetail=stockBasicService.getStockInfoById(stockId);
            if(stockDetail==null)
                continue;
            Double value= Double.parseDouble(stockDetail.get("value").toString());
            entryValue.setNewestRate(value);
        }
    }

    public void updateBasicInfoCacheByStockId(String stockId)
    {
        StockBasicInfo stockBasicInfo=stockBasicInfoDao.getStockBasicInfoByStockId(stockId);
        if(stockBasicInfo!=null)
        {
            System.out.println("update stock index:");
            System.out.println(stockBasicInfo.getStockPrice());
            basicInfoHashMap.replace(stockBasicInfo.getStockId(),stockBasicInfo);
        }
    }
}
