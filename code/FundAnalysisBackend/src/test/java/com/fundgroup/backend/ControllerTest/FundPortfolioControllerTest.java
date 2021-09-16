package com.fundgroup.backend.ControllerTest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.utils.messageUtils.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FundPortfolioControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetFundPortfolioByCode()
    {
        Message result = restTemplate.getForObject("/anyone/fund/portfolio?fundCode=000001", Message.class);
        JSONObject jsonObject=result.getData();
        JSONArray jsonArray=jsonObject.getJSONArray("fundPortfolioList");
        Assert.isTrue(jsonArray.size() > 0,"fund portfolio must have positive stock");
    }
}
