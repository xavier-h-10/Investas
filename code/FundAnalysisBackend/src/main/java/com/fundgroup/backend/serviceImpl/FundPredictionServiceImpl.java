package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.dao.FundPredictionDao;
import com.fundgroup.backend.dto.FundPredictionDisplay;
import com.fundgroup.backend.entity.FundPrediction;
import com.fundgroup.backend.service.FundPredictionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FundPredictionServiceImpl implements FundPredictionService {
  FundPredictionDao fundPredictionDao;

  @Autowired
  void setFundPredictionDao(FundPredictionDao fundPredictionDao){
    this.fundPredictionDao = fundPredictionDao;
  }

//  @Override
//  public List<FundPredictionDisplay> getFundPredictions(Integer day) {
//    return fundPredictionDao.getFundPredictions(day);
//  }
//
//  @Override
//  public List<FundPredictionDisplay> getFundPredictionsByType(Integer fundType, Integer day) {
//    return fundPredictionDao.getFundPredictionsByFundType(fundType, day);
//  }
}
