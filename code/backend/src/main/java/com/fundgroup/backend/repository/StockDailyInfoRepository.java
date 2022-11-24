package com.fundgroup.backend.repository;

import com.fundgroup.backend.dto.FundDailySimp;
import com.fundgroup.backend.dto.StockDailySimp;
import com.fundgroup.backend.entity.StockDailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public interface StockDailyInfoRepository extends JpaRepository<StockDailyInfo, BigInteger> {
    @Query("select new com.fundgroup.backend.dto.StockDailySimp(s.updateDate,s.stockPrice) from StockDailyInfo s where s.stockId=:stockId order by s.updateDate")
    List<StockDailySimp> getAllByStockId(@Param("stockId") String stockId);

    @Query("select new com.fundgroup.backend.dto.StockDailySimp(s.updateDate,s.stockPrice) from StockDailyInfo s where (s.stockId=:stockId and s.updateDate>=:beginDate and s.updateDate<=:endDate) order by s.updateDate")
    List<StockDailySimp> getStockDailyRangeSimp(@Param("stockId") String stockId, @Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

    @Query("select s from StockDailyInfo s where (s.stockId =:stockId and s.updateDate=:date)")
    StockDailyInfo getStockDailyByCodeDate(@Param("stockId") String stockId, @Param("date") LocalDate date);

    StockDailyInfo findFirstByStockIdOrderByUpdateDate(String stockId);
}
