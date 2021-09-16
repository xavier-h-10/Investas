package com.fundgroup.backend.repository;

import com.fundgroup.backend.entity.StockBasicInfo;
import com.fundgroup.backend.entity.StockDailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockBasicInfoRepository extends JpaRepository<StockBasicInfo, String> {
    @Query("select s from StockBasicInfo s where s.stockId=:stockId")
    StockBasicInfo getStockBasicInfoByStockId(@Param("stockId") String stockID);

    @Query("select s from StockBasicInfo s")
    List<StockBasicInfo> getAllStockBasic();

}
