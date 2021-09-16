package com.fundgroup.backend.ControllerTest;

import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.controller.FundIndicatorController;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FundIndicatorControllerTest {
  @Autowired
  private FundIndicatorController fundIndicatorController;

  @Test
  public void testGetFundCard() {
    fundIndicatorController.fetchFundIndicator();
  }

  @Test
  public void getHomeFundIndicator() {
    fundIndicatorController.getHomeFundIndicator(-10, 10, 10, -10, 0, 10);
  }

  @Test
  public void processData() {
    fundIndicatorController.processData();
  }

  @Test
  public void getFundIndicatorByCode() {
    fundIndicatorController.getFundIndicatorByCode("000689");
  }

  @Test
  public void getFundIndicatorNumber() {
    fundIndicatorController.getFundIndicatorNumber("000689");
  }
}
