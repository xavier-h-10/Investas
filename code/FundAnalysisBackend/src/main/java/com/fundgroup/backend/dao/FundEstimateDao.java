package com.fundgroup.backend.dao;


import com.fundgroup.backend.dto.FundEstimateSimp;

import java.time.LocalDateTime;
import java.util.List;

public interface FundEstimateDao {
    List<FundEstimateSimp> getFundEstimateRange(String fundCode, LocalDateTime beginTime, LocalDateTime endTime);
    void deleteRange(LocalDateTime beginTime, LocalDateTime endTime);
}
