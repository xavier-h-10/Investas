package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dao.HomeRankFundDao;
import com.fundgroup.backend.service.FundPredictionErrorService;
import com.fundgroup.backend.utils.messageUtils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO: fix access
@RestController
public class FundPredictionErrorController {

  @Autowired
  FundPredictionErrorService fundPredictionErrorService;

  @Autowired
  HomeRankFundDao homeRankFundDao;

  @RequestMapping(value = "/anyone/predictionError/getErrorMonitorData", method = RequestMethod.GET)
  public Message getErrorMonitorData() {
//    接口1： 获得最近的所有误差和数量
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("src", fundPredictionErrorService.getErrorMonitorData());

    return new Message(0, "获取监控数据成功", jsonObject);
  }

  //  接口2，3：获得误差最小和最大的前15名
  @RequestMapping(value = "/anyone/predictionError/getErrorMax", method = RequestMethod.GET)
  public Message getErrorMax() {

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("src", fundPredictionErrorService.getErrorMax());

    return new Message(0, "获取数据成功", jsonObject);
  }

  @RequestMapping(value = "/anyone/predictionError/getErrorMin", method = RequestMethod.GET)
  public Message getErrorMin() {

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("src", fundPredictionErrorService.getErrorMin());

    return new Message(0, "获取数据成功", jsonObject);
  }

  //  接口4： 给定一个fund code得到最近的误差和平均误差，画图
  @RequestMapping(value = "/anyone/predictionError/getErrorByCode", method = RequestMethod.GET)
  public Message getErrorByCode(@RequestParam("fundCode") String fundCode) {

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("src", fundPredictionErrorService.getErrorByCode(fundCode));

    return new Message(0, "获取基金误差数据成功", jsonObject);
  }

  @RequestMapping(value = "/anyone/predictionFundView/getPredictionFrontendView", method = RequestMethod.GET)
  public Message getPredictionFrontendView(@RequestParam("fundCode") String fundCode) {

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("src", homeRankFundDao.getPredictionViewByFundCode(fundCode));

    return new Message(0, "获取基金前端预测视图成功", jsonObject);
  }


}
