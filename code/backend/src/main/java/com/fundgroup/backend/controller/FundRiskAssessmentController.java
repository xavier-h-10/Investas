package com.fundgroup.backend.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.entity.FundRiskAssessment;
import com.fundgroup.backend.service.FundRiskAssessmentService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FundRiskAssessmentController {

  @Autowired
  private FundRiskAssessmentService fundRiskAssessmentService;

  @RequestMapping(value = "/anyone/riskAssessment", method = RequestMethod.GET)
  public Message getRiskAssessment() {
    List<FundRiskAssessment> list = fundRiskAssessmentService.getAll();
    return new Message(MessageUtil.SUCCESS, JSONObject.toJSONString(list));
  }

}
