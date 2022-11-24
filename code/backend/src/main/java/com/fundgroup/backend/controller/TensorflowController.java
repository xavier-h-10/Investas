package com.fundgroup.backend.controller;

import com.fundgroup.backend.service.TensorflowService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.tensorflowUtils.TensorflowUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TensorflowController {

  TensorflowService tensorflowService;

  private static final Logger logger = LoggerFactory.getLogger(TensorflowController.class);

  @Autowired
  void setTensorflowService(TensorflowService tensorflowService)
  {
    this.tensorflowService = tensorflowService;
  }

  @RequestMapping(value = "/anyone/updatePredictions")
  public Message updatePredictions()
  {
    tensorflowService.update();
    return new Message(0, "更新成功");
  }

  @RequestMapping(value = "/anyone/updatePredictionError")
  public Message updatePredictionError()
  {
    tensorflowService.calculateError();
    return new Message(0, "更新成功");
  }

  @RequestMapping(value = "/anyone/getPredictions/TopRankings")
  public Message getPredictionTopRanking()
  {
    return new Message(0);
  }

  @RequestMapping(value = "/anyone/updatePredictionErrors")
  public Message updatePredictionErrors()
  {
    tensorflowService.calculateError();
    return new Message(0, "执行完成");
  }
}
