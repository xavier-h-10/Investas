package com.fundgroup.backend.service;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dto.FundDailySimp;
import com.fundgroup.backend.entity.FundDailyInfo;

import java.util.List;

public interface FundDailyInfoService {
    List<FundDailySimp> getFundDailyTimeRange(String fundCode, TimeType timeType);
    List<FundDailyInfo> getFundDailyPage(String fundCode, Integer page, Integer size);
    List<FundDailySimp> getFundDailyDetailFourPage(String fundCode, Integer page, Integer size);
    boolean checkDailyUpdateComplete();
    void missingDateInterpolation();
}
