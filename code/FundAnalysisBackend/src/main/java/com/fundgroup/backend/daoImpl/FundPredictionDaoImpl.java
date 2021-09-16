package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.FundPredictionDao;
import com.fundgroup.backend.dto.FundPredictionDisplay;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundPrediction;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import com.fundgroup.backend.repository.FundDailyInfoRepository;
import com.fundgroup.backend.repository.FundPredictionRepository;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FundPredictionDaoImpl implements FundPredictionDao {

  FundPredictionRepository fundPredictionRepository;

  FundBasicInfoRepository fundBasicInfoRepository;

  FundDailyInfoRepository fundDailyInfoRepository;

  @Autowired
  void setFundPredictionRepository(FundPredictionRepository fundPredictionRepository){
    this.fundPredictionRepository = fundPredictionRepository;
  }

  @Autowired
  void setFundBasicInfoRepository(FundBasicInfoRepository fundBasicInfoRepository){
    this.fundBasicInfoRepository = fundBasicInfoRepository;
  }

  @Autowired
  void setFundDailyInfoRepository(FundDailyInfoRepository fundDailyInfoRepository){
    this.fundDailyInfoRepository = fundDailyInfoRepository;
  }

  @Override
  public void updatePredictedNAV(Integer fundType, String fundCode, double originNAV, double oneDayNAV,
      double twoDayNAV, double threeDayNAV) {
    if(originNAV == 0) {
//      TODO: MAY BE a bug here
      return;
    }

    double oneDayRate = ((oneDayNAV - originNAV) / originNAV) * 100;
    double twoDayRate = ((twoDayNAV - originNAV) / originNAV) * 100;
    double threeDayRate = ((threeDayNAV - originNAV) / originNAV) * 100;


    LocalDateTime localDate = LocalDateTime.now();

    FundPrediction fundPrediction = fundPredictionRepository.findById(fundCode)
        .orElse(new FundPrediction(fundCode));

    fundPrediction.setFundType(fundType);
    fundPrediction.setLastUpdateTimestamp(localDate);
    fundPrediction.setFutureOneDayNAV(oneDayNAV);
    fundPrediction.setFutureTwoDaysNAV(twoDayNAV);
    fundPrediction.setFutureThreeDaysNAV(threeDayNAV);
    fundPrediction.setFutureOneDayChange(oneDayRate);
    fundPrediction.setFutureTwoDaysChange(twoDayRate);
    fundPrediction.setFutureThreeDaysChange(threeDayRate);

    fundPredictionRepository.saveAndFlush(fundPrediction);
  }
//
//  @Override
//  public List<FundPredictionDisplay> getFundPredictions(Integer day) {
//    List<FundPrediction> fundPredictions = new ArrayList<>();
//
//    if(day.equals(3)) {
//      fundPredictions = fundPredictionRepository.getTopFutureFundByThreeDay();
//    }
//    else{
//      fundPredictions = fundPredictionRepository.getTopFutureFundByOneDay();
//    }
//
//    return getFundPredictionDisplay(fundPredictions);
//  }
//
//  @Override
//  public List<FundPredictionDisplay> getFundPredictionsByFundType(Integer FundType, Integer day) {
//    List<FundPrediction> fundPredictions = new ArrayList<>();
//    if(day.equals(3)) {
//      fundPredictions =  fundPredictionRepository.getTopFutureFundByTypeByThreeDay(FundType);
//    }
//    else{
//      fundPredictions =  fundPredictionRepository.getTopFutureFundByTypeByOneDay(FundType);
//    }
//
//    return getFundPredictionDisplay(fundPredictions);
//  }
//
//  private List<FundPredictionDisplay> getFundPredictionDisplay(List<FundPrediction> fundPredictions){
//    List<FundPredictionDisplay> fundPredictionDisplays = new ArrayList<>();
//    for(FundPrediction fundPrediction: fundPredictions){
//      FundPredictionDisplay fundPredictionDisplay = new FundPredictionDisplay(fundPrediction);
//      fundPredictionDisplay.setFundName(fundBasicInfoRepository.getFundNameByFundCode(fundPrediction.getFundCode()));
//      List<Double> navs = fundDailyInfoRepository.getNewestNAVs(fundPrediction.getFundCode(), 2);
//      if(navs.size() < 2){
//        continue;
//      }
//      fundPredictionDisplay.setOriginNAV(navs.get(0));
//      Double originQuote = (navs.get(0) / navs.get(1));
//      DecimalFormat df = new DecimalFormat("#.00");
//      df.format(originQuote);
//      fundPredictionDisplay.setOriginQuote(originQuote);
//      fundPredictionDisplays.add(fundPredictionDisplay);
//    }
//
//    return fundPredictionDisplays;
//  }


  @Override
  public FundPrediction getOne(String fundCode) {
    return fundPredictionRepository.findById(fundCode).orElse(null);
  }
}
