package com.fundgroup.backend.dao;

import com.fundgroup.backend.dto.StockDailySimp;
import com.fundgroup.backend.entity.StockDailyInfo;

import java.time.LocalDate;
import java.util.List;


public interface StockDailyInfoDao {
    List<StockDailySimp> getAllByStockId(String stockId);
    List<StockDailySimp> getStockDailyRangeSimp(String stockId, LocalDate beginDate, LocalDate endDate);
    StockDailyInfo getStockDailyByCodeDate(String stockId,LocalDate date);
    StockDailyInfo findFirstByStockIdOrderByUpdateDate(String stockId);
    void saveStockDailyInfo(StockDailyInfo stockDailyInfo);
}
