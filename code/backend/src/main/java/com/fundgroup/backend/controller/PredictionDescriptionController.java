package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.fundgroup.backend.dto.ShowPredictionDescription;
import com.fundgroup.backend.repository.FundPredictionRepository;
import com.fundgroup.backend.service.PredictionDescriptionService;
import com.fundgroup.backend.utils.messageUtils.Message;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.security.auth.kerberos.KerberosKey;
import net.sf.jsqlparser.statement.ShowColumnsStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PredictionDescriptionController {

  @Autowired
  PredictionDescriptionService predictionDescriptionService;

  @Autowired
  FundPredictionRepository fundPredictionRepository;

//  TODO: Fix access
  @RequestMapping(value = "/anyone/description/createDescription",
      method = RequestMethod.POST)
  public Message createDescription(@RequestBody JSONObject param) {
      JSONObject basicInfo = param.getJSONObject("basicInfo");

      JSONArray rules = param.getJSONArray("rule");

    predictionDescriptionService.createDescription(basicInfo, rules);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("src", predictionDescriptionService.getAll());
    return new Message(0, "创建规则成功", jsonObject);
  }

  //  TODO: Fix access
  @RequestMapping(value = "/anyone/description/getAllDescription",
      method = RequestMethod.GET)
  public Message getAllDescription() {

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("src", predictionDescriptionService.getAll());


    return new Message(0, "创建规则成功", jsonObject);
  }

  //  TODO: Fix access
  @RequestMapping(value = "/anyone/description/deleteDescription",
      method = RequestMethod.POST)
  public Message deleteDescription(@RequestBody JSONObject param) {

    Integer id = param.getInteger("value");

    predictionDescriptionService.deleteDescription(id);

    return new Message(0, "删除成功");
  }

  //  TODO: Fix access
  @RequestMapping(value = "/anyone/description/updateDescription",
      method = RequestMethod.POST)
  public Message updateDescription() {
    predictionDescriptionService.updateAllPredictionDescription();

    return new Message(0, "更新成功");
  }

  //  TODO: Fix access
  @RequestMapping(value = "/anyone/description/getReportData",
      method = RequestMethod.GET)
  public Message getReportData() {

    List<ShowPredictionDescription> descriptions = fundPredictionRepository.getFundPredictionDescription();
    for(ShowPredictionDescription showPredictionDescription: descriptions){
      showPredictionDescription.setName(predictionDescriptionService.getNameById(showPredictionDescription.getId()));
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("src", descriptions);

    return new Message(0, "获取数据成功", jsonObject);
  }
}
