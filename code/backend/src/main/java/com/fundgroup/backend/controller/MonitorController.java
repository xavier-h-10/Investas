package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.entity.FundModel;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import com.fundgroup.backend.repository.FundCompetitionRepository;
import com.fundgroup.backend.repository.FundDailyInfoRepository;
import com.fundgroup.backend.repository.FundEstimateRepository;
import com.fundgroup.backend.repository.FundIndicatorRepository;
import com.fundgroup.backend.repository.FundModelRepository;
import com.fundgroup.backend.repository.FundPredictionRepository;
import com.fundgroup.backend.repository.FundRateRepository;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitorController {

  @Autowired
  FundPredictionRepository fundPredictionRepository;

  @Autowired
  FundDailyInfoRepository fundDailyInfoRepository;

  @Autowired
  FundEstimateRepository fundEstimateRepository;

  @Autowired
  FundIndicatorRepository fundIndicatorRepository;

  @Autowired
  FundBasicInfoRepository fundBasicInfoRepository;

  @Autowired
  FundCompetitionRepository fundCompetitionRepository;

  @Autowired
  FundRateRepository fundRateRepository;

  @Autowired
  FundModelRepository fundModelRepository;

//  TODO: change access
  @RequestMapping(value = "/anyone/monitor/getMonitorData", method = RequestMethod.GET)
  public Message getMonitorData() {
    Pageable pageable = PageRequest.of(0, 30);
    LocalDateTime localDateTime = LocalDateTime.now();
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("latestPredictionData", fundPredictionRepository.getMonitorDate(localDateTime));
//    jsonObject.put("NAVData", fundDailyInfoRepository.getMonitorDate());
    jsonObject.put("historyData", fundDailyInfoRepository.getMonitorHistory(pageable));
    jsonObject.put("basicFundTypeData", fundBasicInfoRepository.getTypeAndNum());
//    jsonObject.put("fundCompetitionData", fundCompetitionRepository.getMonitorData());
//    jsonObject.put("fundRateData", fundRateRepository.getMonitorData());
    return new Message(0, "获取监控数据成功", jsonObject);
  }

//  TODO: Fix access
  @RequestMapping(value = "/anyone/monitor/getWorkplaceData", method = RequestMethod.GET)
  public Message getWorkplaceData() {
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("modelTypeData", fundModelRepository.findAll());
    return new Message(0, "获取工作区成功", jsonObject);
  }

//  TODO: Fix access
  @RequestMapping(value = "/anyone/monitor/setTypeModel", method = RequestMethod.POST)
  public Message setTypeModel(@RequestBody Map<String, String> param) {

    Integer type = Integer.valueOf(param.get("id"));
    FundModel fundModel = fundModelRepository.findById(type).orElse(new FundModel(type));
    fundModel.setFundCode(param.get("code"));

    fundModelRepository.saveAndFlush(fundModel);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("modelTypeData",
        fundModelRepository.findAll());

    return new Message(0, "修改成功", jsonObject);
  }

  @RequestMapping(value = "/anyone/monitor/deleteModel", method = RequestMethod.POST)
  public Message deleteTypeModel(@RequestBody Map<String, String> param) {

    Integer type = Integer.valueOf(param.get("fundType"));
    fundModelRepository.deleteById(type);

    return new Message(0, "删除成功");
  }

}
