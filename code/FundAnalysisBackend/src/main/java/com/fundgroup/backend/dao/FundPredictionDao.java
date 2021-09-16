package com.fundgroup.backend.dao;

import com.fundgroup.backend.dto.FundPredictionDisplay;
import com.fundgroup.backend.entity.FundPrediction;
import java.util.List;

public interface FundPredictionDao {
  void updatePredictedNAV(Integer fundType, String fundCode, double originNAV, double oneDayNAV,
      double twoDayNAV, double threeDayNAV);

//  List<FundPredictionDisplay> getFundPredictions(Integer day);
//
//  List<FundPredictionDisplay> getFundPredictionsByFundType(Integer FundType, Integer day);

  FundPrediction getOne(String fundCode);
}
