package com.fundgroup.backend.service;

import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dto.FundRankingByRecentRate;
import com.fundgroup.backend.entity.FundBasicInfo;

import java.time.LocalDate;
import java.util.List;

public interface FundRateService {
    boolean updateFundRate(LocalDate today);
    JSONObject getFundRecRate(String fundCode);

    JSONObject getFundRateByCode(String fundCode);

    List<FundRankingByRecentRate> getFundByRateRanking(Integer type);
}
