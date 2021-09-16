package com.fundgroup.backend.service;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dto.StockDailySimp;

import java.time.LocalDate;
import java.util.List;

public interface StockDailyInfoService {
    List<StockDailySimp> getStockDailyTimeRange(String stockId, TimeType timeType);
    void missingDateInterpolation();
}
