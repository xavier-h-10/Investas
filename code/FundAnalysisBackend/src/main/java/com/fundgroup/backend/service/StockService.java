package com.fundgroup.backend.service;

import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.entity.StockBasicInfo;

public interface StockService {
    StockBasicInfo getStockBasicInfoByStockId(String stockID);
}
