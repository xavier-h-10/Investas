package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.constant.Mode;
import com.fundgroup.backend.dao.RegisterDao;
import com.fundgroup.backend.dto.FundRankingByRecentRate;
import com.fundgroup.backend.service.FundRateService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestEcsOnlyController {

  @Autowired
  RegisterDao registerDao;

  @GetMapping(value = "/anyone/test")
  public Message testLink() {
    return new Message(MessageUtil.SUCCESS, "success", null);
  }

  @GetMapping(value = "/anyone/test_redis")
  public Message testRedis() {
    registerDao.setAuth("00", "10");
    if(registerDao.checkAuth("00", "10")) {
      return new Message(MessageUtil.SUCCESS, "success", null);
    }
    else{
      return new Message(MessageUtil.SUCCESS, "fail", null);
    }
  }

}


