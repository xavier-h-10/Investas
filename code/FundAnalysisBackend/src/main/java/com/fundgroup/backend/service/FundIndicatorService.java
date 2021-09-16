package com.fundgroup.backend.service;

import com.alibaba.fastjson.JSONArray;
import com.fundgroup.backend.dto.HomeFundIndicator;
import com.fundgroup.backend.entity.FundIndicator;
import java.util.List;

public interface FundIndicatorService {
  void fetchFundIndicator();

  JSONArray getFundIndicatorByCode(String fundCode);

  void processData();

  Runnable wrappedUpdateService();

  List<Integer> getFundIndicatorNumber(String fundCode);

  List<HomeFundIndicator> getHomeFundIndicator(Integer sharp, Integer maxRet, Integer stdDev, Integer profit,
                                               Integer page, Integer size);
}
