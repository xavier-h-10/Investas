package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.StockDailyInfoDao;
import com.fundgroup.backend.dto.StockDailySimp;
import com.fundgroup.backend.entity.StockDailyInfo;
import com.fundgroup.backend.repository.StockDailyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class StockDailyInfoDaoImpl implements StockDailyInfoDao {
    @Autowired
    private StockDailyInfoRepository stockDailyInfoRepository;

    @Override
    public List<StockDailySimp> getAllByStockId(String stockId)
    {
        return stockDailyInfoRepository.getAllByStockId(stockId);
    }

    @Override
    public List<StockDailySimp> getStockDailyRangeSimp(String stockId, LocalDate beginDate, LocalDate endDate)
    {
        return stockDailyInfoRepository.getStockDailyRangeSimp(stockId,beginDate,endDate);
    }

    @Override
    public StockDailyInfo getStockDailyByCodeDate(String stockId, LocalDate date)
    {
        return stockDailyInfoRepository.getStockDailyByCodeDate(stockId,date);
    }

    @Override
    public StockDailyInfo findFirstByStockIdOrderByUpdateDate(String stockId)
    {
        return stockDailyInfoRepository.findFirstByStockIdOrderByUpdateDate(stockId);
    }

    @Override
    public void saveStockDailyInfo(StockDailyInfo stockDailyInfo)
    {
        stockDailyInfoRepository.save(stockDailyInfo);
    }
}
