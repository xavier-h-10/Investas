package com.fundgroup.backend.dao;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.entity.FundBasicInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FundBasicInfoDao {

  List<FundBasicInfo> searchFundByCodeOrName(String searchStr, Integer pageIdx, Integer pageSize);

  List<String> getAllCode();

  List<FundBasicInfo> getAllFund();

  FundBasicInfo getOne(String fundCode);

  Integer countFundNumTimeRange(TimeType timeType);

  Integer getFundNumber();

  List<Object> getAllFundInfo();
}
