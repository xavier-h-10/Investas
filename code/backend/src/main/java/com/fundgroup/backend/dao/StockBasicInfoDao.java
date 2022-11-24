package com.fundgroup.backend.dao;

import com.fundgroup.backend.entity.StockBasicInfo;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockBasicInfoDao {
    StockBasicInfo getStockBasicInfoByStockId(String stockID);
    List<StockBasicInfo> getAllStockBasic();
}
