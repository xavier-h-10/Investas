package com.fundgroup.backend.dao;

import com.alibaba.fastjson.JSONArray;
import com.fundgroup.backend.dto.HomeFundIndicator;
import com.fundgroup.backend.entity.FundIndicator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundIndicatorDao {
  void fetchFundIndicator();

  void processData();

  JSONArray getFundIndicatorByCode(String fundCode);

  List<Integer> getFundIndicatorNumber(String fundCode);

  List<HomeFundIndicator> getHomeFundIndicator(Integer sharp, Integer maxRet, Integer stdDev, Integer profit,
                                               Integer page, Integer size);
}
