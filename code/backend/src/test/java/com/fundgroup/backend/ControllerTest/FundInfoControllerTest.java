package com.fundgroup.backend.ControllerTest;

import com.fundgroup.backend.controller.FundInfoController;
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
public class FundInfoControllerTest {
  @Autowired
  private FundInfoController fundInfoController;

  @Test
  public void testGetFundCard() {
    Message result=fundInfoController.getFundCard("000001");
    Assert.assertEquals(result.getStatus(), MessageUtil.SUCCESS);

    result=fundInfoController.getFundCard("000000");
    Assert.assertEquals(result.getStatus(),MessageUtil.ERROR);
  }

  @Test
  public void testSearchFundSimpInfo() {
    Message result=fundInfoController.searchFundSimpInfo("test",-1);
    Assert.assertEquals(result.getStatus(),MessageUtil.INVALID_ARGUMENT);

    result=fundInfoController.searchFundSimpInfo("易方达",1);
    Assert.assertEquals(result.getStatus(),MessageUtil.SUCCESS);
  }


}
