package com.fundgroup.backend.ControllerTest;

import com.alibaba.fastjson.JSON;
import com.fundgroup.backend.constant.Mode;
import com.fundgroup.backend.controller.FundRateController;
import com.fundgroup.backend.utils.messageUtils.Message;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FundRateControllerTest {
  @Autowired
  private TestRestTemplate restTemplate;

//  @Test
//  public void testFundRateMethod(){
//    Map<String, String> mp = new HashMap<>();
//    Message result = restTemplate.postForObject("/anyone/fund-rate", null, Message.class, mp);
//    System.out.println(result.getData().toString());
//  }

//  @Test
//  public void testFundRateMethod(){
//    Map<String, String> mp = new HashMap<>();
//    mp.put("fundCode", "000689");
//    mp.put("mode", String.valueOf(Mode.SHOW_PART));
//    String paras = JSON.toJSONString(mp);
//    Message result = restTemplate.postForObject("/anyone/fund/recent-rate",
//        MediaType.APPLICATION_JSON_VALUE, Message.class, paras);
//    System.out.println(result.getData().toString());
//  }


  @Autowired
  FundRateController fundRateController;

  @Test
  public void updateFundRate(){
    Map<String, Integer> params = new HashMap<>();
    params.put("year", 2021);
    params.put("month", 8);
    params.put("day", 1);
    fundRateController.updateFundRate(params);
  }

  @Test
  public void TestGetFundRecentRate(){
//    Paras=
    String fundCode = "000689";
    Integer mode = Mode.SHOW_PART;
    fundRateController.getFundRecentRate(fundCode, mode);
    mode = Mode.SHOW_ALL;
    fundRateController.getFundRecentRate(fundCode, mode);
    mode = 2;
    fundRateController.getFundRecentRate(fundCode, mode);
  }

  @Test
  public void TestGetTopFundByRanking(){
    Map<String, Integer> params = new HashMap<>();
    params.put("type", 0);
    fundRateController.getTopFundByRanking(params);
    params.put("type", 1);
    fundRateController.getTopFundByRanking(params);
    params.put("type", 2);
    fundRateController.getTopFundByRanking(params);
  }
}
