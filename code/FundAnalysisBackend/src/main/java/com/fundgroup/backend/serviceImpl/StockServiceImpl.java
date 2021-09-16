package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.cache.StockCache;
import com.fundgroup.backend.dao.StockBasicInfoDao;
import com.fundgroup.backend.entity.StockBasicInfo;
import com.fundgroup.backend.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockBasicInfoDao stockBasicInfoDao;

    @Override
    public StockBasicInfo getStockBasicInfoByStockId(String stockID)
    {
        return stockBasicInfoDao.getStockBasicInfoByStockId(stockID);
    }

}
