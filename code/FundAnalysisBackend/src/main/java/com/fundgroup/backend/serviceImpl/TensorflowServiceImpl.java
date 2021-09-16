package com.fundgroup.backend.serviceImpl;

import static com.fundgroup.backend.utils.tensorflowUtils.Tensor_Constant.SEQ_LEN;

import com.fundgroup.backend.controller.TensorflowController;
import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.dao.FundPredictionDao;
import com.fundgroup.backend.dto.FundCodeType;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundModel;
import com.fundgroup.backend.entity.FundPrediction;
import com.fundgroup.backend.entity.FundPredictionError;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import com.fundgroup.backend.repository.FundModelRepository;
import com.fundgroup.backend.repository.FundPredictionErrorRepository;
import com.fundgroup.backend.service.TensorflowService;
import com.fundgroup.backend.utils.tensorflowUtils.TensorflowUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.apache.hadoop.yarn.webapp.hamlet2.Hamlet.LI;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import org.springframework.stereotype.Service;
import scala.Int;

@Service
public class TensorflowServiceImpl implements TensorflowService {

  FundBasicInfoRepository fundBasicInfoRepository;

  FundDailyInfoDao fundDailyInfoDao;

  FundPredictionDao fundPredictionDao;

  FundModelRepository fundModelRepository;

  TensorflowUtils tensorflowUtils;

  FundPredictionErrorRepository fundPredictionErrorRepository;

  private static final Logger logger = LoggerFactory.getLogger(TensorflowService.class);

  @Autowired
  void setFundPredictionErrorRepository(FundPredictionErrorRepository fundPredictionErrorRepository){
    this.fundPredictionErrorRepository = fundPredictionErrorRepository;
  }

  @Autowired
  void setFundModelRepository(FundModelRepository fundModelRepository){
    this.fundModelRepository = fundModelRepository;
  }


  @Autowired
  void setFundBasicInfoRepository(FundModelRepository fundModelRepository) {
    this.fundModelRepository = fundModelRepository;
  }

  @Autowired
  void setFundBasicInfoRepository(FundBasicInfoRepository fundBasicInfoRepository) {
    this.fundBasicInfoRepository = fundBasicInfoRepository;
  }

  @Autowired
  void setFundDailyInfoDao(FundDailyInfoDao fundDailyInfoDao) {
    this.fundDailyInfoDao = fundDailyInfoDao;
  }

  @Autowired
  void setFundPredictionDao(FundPredictionDao fundPredictionDao) {
    this.fundPredictionDao = fundPredictionDao;
  }

  @Autowired
  void setTensorflowUtils(TensorflowUtils tensorflowUtils) {
    this.tensorflowUtils = tensorflowUtils;
  }

  @Override
  public void update() {
    List<FundCodeType> fundCodeTypeList = fundBasicInfoRepository.getAllFundCodeType();

//    If no model for this fund, use model of same type.
    HashMap<Integer, String> mp = new HashMap<>();

    List<FundModel> fundModels = fundModelRepository.findAll();

    logger.info(String.format("Begin update all fund predictions: total = %d", fundModels.size()));

    for (FundModel fundModel : fundModels) {
      mp.put(fundModel.getFundType(), fundModel.getFundCode());
    }

    for (FundCodeType fundCodeType : fundCodeTypeList) {
      if(fundCodeType.getHas_model().equals(false)){
      if (fundCodeType.getFundType().equals(13) || fundCodeType.getFundType().equals(14)
      || fundCodeType.getFundType().equals(6)) {
        logger.info(String.format("Over a fund type = 13 / 14 / 6, fund code = %s", fundCodeType.getFundCode()));
        continue;
      }

      String model_code = mp.get(fundCodeType.getFundType());
      tensorflowUtils.startDockerService(model_code, fundCodeType.getFundType());
      }
      else{
        tensorflowUtils.startDockerService(fundCodeType.getFundCode(), fundCodeType.getFundType());
      }

//    time: earlier - later
      List<Double> inputData = fundDailyInfoDao.getNewestNAVs(fundCodeType.getFundCode(), SEQ_LEN);
      Collections.reverse(inputData);

      if (inputData.size() < SEQ_LEN) {
        fundPredictionDao
            .updatePredictedNAV(fundCodeType.getFundType(), fundCodeType.getFundCode(), -1.0,
                -1.0, -1.0, -1.0);
      } else {
        for (int i = 0; i < 3; ++i) {
          try {
            tensorflowUtils.execPrediction(inputData);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
//        System.out.println(fundCodeType.getFundType().toString() + fundCodeType.getFundCode() +
//            inputData.get(SEQ_LEN - 4).toString() +
//            inputData.get(SEQ_LEN - 3).toString() + inputData.get(SEQ_LEN - 2).toString()
//            + inputData.get(SEQ_LEN - 1).toString());
        fundPredictionDao.updatePredictedNAV(fundCodeType.getFundType(), fundCodeType.getFundCode(),
            inputData.get(SEQ_LEN - 4),
            inputData.get(SEQ_LEN - 3), inputData.get(SEQ_LEN - 2), inputData.get(SEQ_LEN - 1));
      }

      logger.info(String.format("complete a fund, fund code = %s", fundCodeType.getFundCode()));
    }

    logger.info("prediction complete");
  }

  @Override
  public void calculateError() {
//    此函数应该在TFS更新前调用

//    自动认为: 1. NAV已经更新为最新， 2. 执行当天应当更新
    List<FundCodeType> fundCodeTypeList = fundBasicInfoRepository.getAllFundCodeType();
    LocalDateTime localDateTime = LocalDateTime.now();
    logger.info("start fund calculate error");

    for (FundCodeType fundCodeType : fundCodeTypeList) {

//      fix bug: if type == 6 / 13 / 14, or input data size < 3 or fundPrediction == null
//      continue.
      List<Double> inputData = fundDailyInfoDao.getNewestNAVs(fundCodeType.getFundCode(), 3);

      FundPrediction fundPrediction = fundPredictionDao.getOne(fundCodeType.getFundCode());

      if(fundPrediction == null || fundPrediction.getFutureOneDayNAV() == -1 || inputData.size() < 1 ||
      fundCodeType.getFundType() == 6 || fundCodeType.getFundType() == 13 ||
      fundCodeType.getFundType() == 14){
        logger.info(String.format("jump over a fund to calculate error, fund code = %s", fundCodeType.getFundCode()));
        continue;
      }

      FundPredictionError fundPredictionError = new FundPredictionError(fundCodeType.getFundCode(),
          fundCodeType.getFundType(), localDateTime);
//      then write our data
      fundPredictionError.setTodayMSE(calculateMSE(fundPrediction.getFutureOneDayNAV(), inputData.get(0)));
      fundPredictionError.setTodayRMSE(calculateRMSE(fundPrediction.getFutureOneDayNAV(), inputData.get(0)));
      fundPredictionError.setTodayMAE(calculateMAE(fundPrediction.getFutureOneDayNAV(), inputData.get(0)));
      fundPredictionError.setTodayAbsDelta(calculateDelta(fundPrediction.getFutureOneDayNAV(), inputData.get(0)));

      fundPredictionErrorRepository.saveAndFlush(fundPredictionError);
    }
    logger.info("complete fund error calculate.");
  }

  private double calculateMSE(double real, double prediction) {
    return Math.pow((real - prediction), 2);
  }

  private double calculateRMSE(double real, double prediction) {
    return Math.sqrt(Math.pow((real - prediction), 2));
  }

  private double calculateMAE(double real, double prediction) {
    return Math.abs((prediction - real)) * 1000;
  }

  private double calculateDelta(double real, double prediction){
    return ((prediction - real) / prediction) * 1000.0;
  }
}
