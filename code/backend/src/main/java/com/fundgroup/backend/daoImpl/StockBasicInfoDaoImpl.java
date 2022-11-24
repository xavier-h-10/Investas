package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.StockBasicInfoDao;
import com.fundgroup.backend.entity.StockBasicInfo;
import com.fundgroup.backend.repository.StockBasicInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockBasicInfoDaoImpl implements StockBasicInfoDao {
    @Autowired
    private StockBasicInfoRepository stockBasicInfoRepository;

    @Override
    public StockBasicInfo getStockBasicInfoByStockId(String stockID)
    {
        return stockBasicInfoRepository.getStockBasicInfoByStockId(stockID);
    }

    @Override
    public List<StockBasicInfo> getAllStockBasic()
    {
        return stockBasicInfoRepository.getAllStockBasic();
    }
}
