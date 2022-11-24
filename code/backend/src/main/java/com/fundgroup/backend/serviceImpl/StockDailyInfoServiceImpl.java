package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dao.StockBasicInfoDao;
import com.fundgroup.backend.dao.StockDailyInfoDao;
import com.fundgroup.backend.dto.StockDailySimp;
import com.fundgroup.backend.entity.StockBasicInfo;
import com.fundgroup.backend.entity.StockDailyInfo;
import com.fundgroup.backend.service.StockDailyInfoService;
import com.fundgroup.backend.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockDailyInfoServiceImpl implements StockDailyInfoService {
    @Autowired
    private StockDailyInfoDao stockDailyInfoDao;
    @Autowired
    private StockBasicInfoDao stockBasicInfoDao;

    public List<StockDailySimp> getStockDailyTimeRange(String stockId, TimeType timeType)
    {
        LocalDate today=LocalDate.now();
        LocalDate beginDate= DateUtils.minusDayByTimeType(today,timeType);
        return stockDailyInfoDao.getStockDailyRangeSimp(stockId,beginDate,today);
    }

    public void missingDateInterpolation()
    {
        List<StockBasicInfo> stockBasicInfos=stockBasicInfoDao.getAllStockBasic();
        if(stockBasicInfos.isEmpty())
            return;
        for(StockBasicInfo stockBasicInfo:stockBasicInfos)
        {
            String stockId=stockBasicInfo.getStockId();
            LocalDate pointerDate=stockDailyInfoDao.findFirstByStockIdOrderByUpdateDate(stockId).getUpdateDate();
            LocalDate today=LocalDate.now();
            while(pointerDate.isBefore(today))
            {
                StockDailyInfo stockDailyInfoPointer=stockDailyInfoDao.getStockDailyByCodeDate(stockId,pointerDate);
                LocalDate nextDate=pointerDate.plusDays(1);
                StockDailyInfo stockDailyInfoNext=stockDailyInfoDao.getStockDailyByCodeDate(stockId,nextDate);
                if(stockDailyInfoNext==null)
                {
                    StockDailyInfo stockDailyInfo=new StockDailyInfo(stockDailyInfoPointer.getStockId(),nextDate,
                            stockDailyInfoPointer.getStockPrice(),stockDailyInfoPointer.getUpperPrice(),stockDailyInfoPointer.getLowerPrice());
                    stockDailyInfoDao.saveStockDailyInfo(stockDailyInfo);
                }
                pointerDate=nextDate;
            }
        }
    }
}
