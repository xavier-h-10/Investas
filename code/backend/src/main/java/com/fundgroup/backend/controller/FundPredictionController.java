package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dto.FundPredictionDisplay;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundPrediction;
import com.fundgroup.backend.service.FundPredictionService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FundPredictionController {

  private FundPredictionService fundPredictionService;

  @Autowired
  void setFundPredictionService(FundPredictionService fundPredictionService){
    this.fundPredictionService = fundPredictionService;
  }

//  @RequestMapping(value = "/anyone/prediction/rankingByType",method = RequestMethod.POST)
//  public Message getRankByType(@RequestParam("fundType") Integer fundType)
//  {
//    List<FundPredictionDisplay> fundPredictions = fundPredictionService.getFundPredictionsByType(fundType, 1);
//
//    return ArrayToMessage(fundPredictions);
//  }
//
//  @RequestMapping(value = "/anyone/prediction/PredictionRanking" ,method = RequestMethod.POST)
//  public Message getRankTopFund()
//  {
//    List<FundPredictionDisplay> fundPredictions = fundPredictionService.getFundPredictions(1);
//    return ArrayToMessage(fundPredictions);
//  }

  private Message ArrayToMessage(List<FundPredictionDisplay>fundPredictions){
    if(fundPredictions.size() == 0){
      return new Message(MessageUtil.ERROR, MessageUtil.ILLEGAL_REQUEST);
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("prediction", fundPredictions);
    System.out.println(JSON.toJSONString(fundPredictions));
    return new Message(MessageUtil.SUCCESS, MessageUtil.SUCCESS_MSG, jsonObject);
  }
}
