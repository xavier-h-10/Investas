package com.fundgroup.backend.service;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dto.FundEstimateSimp;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

public interface FundEstimateService {
    List<FundEstimateSimp> getTodayEstimateByCode(String fundCode);
    void deleteEstimateRange(TimeType timeType);
}
