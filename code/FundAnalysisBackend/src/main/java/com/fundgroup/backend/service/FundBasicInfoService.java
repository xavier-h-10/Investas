package com.fundgroup.backend.service;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dto.FundCard;
import com.fundgroup.backend.entity.FundBasicInfo;

import java.time.LocalDate;
import java.util.List;

public interface FundBasicInfoService {
    List<FundBasicInfo> searchFundByCodeOrName(String searchStr, Integer pageIdx, Integer pageSize);
    FundCard searchFundCardByCode(String fundCode);
}
