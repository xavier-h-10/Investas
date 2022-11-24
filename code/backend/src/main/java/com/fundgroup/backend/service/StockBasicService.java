package com.fundgroup.backend.service;

import com.alibaba.fastjson.JSONObject;

public interface StockBasicService {
    JSONObject getStockInfoById(String stockId);
//    JSONObject getStockInfoByIdUrl2(String stockId);
}
