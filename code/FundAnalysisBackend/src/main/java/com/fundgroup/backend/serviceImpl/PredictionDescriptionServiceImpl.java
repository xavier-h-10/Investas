package com.fundgroup.backend.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dao.FundRateDao;
import com.fundgroup.backend.dao.PredictionDescriptionDao;
import com.fundgroup.backend.entity.FundPrediction;
import com.fundgroup.backend.entity.FundRate;
import com.fundgroup.backend.entity.PredictionDescription;
import com.fundgroup.backend.entity.PredictionDescriptionRule;
import com.fundgroup.backend.repository.FundPredictionRepository;
import com.fundgroup.backend.repository.FundRateRepository;
import com.fundgroup.backend.repository.PredictionDescriptionRepository;
import com.fundgroup.backend.service.PredictionDescriptionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PredictionDescriptionServiceImpl implements PredictionDescriptionService {

  @Autowired
  PredictionDescriptionDao predictionDescriptionDao;

  @Autowired
  FundPredictionRepository fundPredictionRepository;

  @Autowired
  FundRateRepository fundRateRepository;

  @Override
  public void createDescription(JSONObject basicInfo, JSONArray rules) {
    predictionDescriptionDao.createDescription(basicInfo, rules);
  }

  @Override
  public List<PredictionDescription> getAll() {
    return predictionDescriptionDao.getAll();
  }

  @Override
  public void deleteDescription(Integer id) {
    predictionDescriptionDao.deleteDescription(id);
  }

  @Override
  public void updateAllPredictionDescription() {
    List<PredictionDescription> descriptions = predictionDescriptionDao.getAll();
    List<FundPrediction> predictions = fundPredictionRepository.findAll();

    for (FundPrediction fundPrediction : predictions) {
      FundRate fundRate = fundRateRepository.getFundRateByFundCode(fundPrediction.getFundCode());

      if(fundPrediction.getFundType() == 6 || fundRate == null)
        continue;

      for (PredictionDescription predictionDescription : descriptions) {
        boolean hit = true;
        for (PredictionDescriptionRule predictionDescriptionRule : predictionDescription.getRuleList()) {
          Integer type = predictionDescriptionRule.getRuleType();
          Integer orientation = predictionDescriptionRule.getRuleOrientation();
          Double value = predictionDescriptionRule.getRuleValue();
//          complex procedure
          if (!isMatchSpecificRule(fundPrediction, fundRate, type, orientation, value)) {
            hit = false;
            break;
          }
        }

        if (hit) {
          fundPredictionRepository.updateDescription(fundPrediction.getFundCode(),
              predictionDescription.getDescriptionId());
          break;
        }
      }
    }
  }

  //  tool
  boolean isMatchSpecificRule(FundPrediction prediction, FundRate rate,
      Integer type, Integer ori, Double value) {
//    基金类型规则
    if (type == 1) {
      if (ori == 0) {
        return prediction.getFundType() <= value.intValue();
      } else {
        return prediction.getFundType() >= value.intValue();
      }
    } else {
      Double target;
      switch (type) {
        case 2:
          target = rate.getLastOneDay();
          if(target.equals(Double.valueOf("-1.00")))
            return true;
          break;
        case 3:
          target = rate.getLastOneWeek();
          if(target.equals(Double.valueOf("-1.00")))
            return true;
          break;
        case 4:
          target = rate.getLastOneMonth();
          if(target.equals(Double.valueOf("-1.00")))
            return true;
          break;
        case 5:
          target = rate.getLastSixMonths();
          if(target.equals(Double.valueOf("-1.00")))
            return true;
          break;
        case 6:
          target = rate.getLastOneYear();
          if(target.equals(Double.valueOf("-1.00")))
            return true;
          break;
        case 7:
          target = rate.getLastThreeYears();
          if(target.equals(Double.valueOf("-1.00")))
            return true;
          break;
        case 8:
          target = prediction.getFutureOneDayChange();
          break;
        case 9:
          target = prediction.getFutureThreeDaysChange();
          break;
        default:
          return true;
      }
      if (ori == 0) {
        return (target.compareTo(value) <= 0);
      } else {
        return (target.compareTo(value) >= 0);
      }
    }
  }

  @Override
  public String getNameById(Integer id) {
    return predictionDescriptionDao.getNameById(id);
  }
}
