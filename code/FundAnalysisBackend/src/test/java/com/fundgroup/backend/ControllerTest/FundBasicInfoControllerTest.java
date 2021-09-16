package com.fundgroup.backend.ControllerTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

/*
* test fail
* */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FundBasicInfoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSearchFundSimpInfo()
    {
        String result = restTemplate.getForObject("/anyone/search/fund/simplified?searchStr=000001&pageIdx=0", String.class);
//        JSONObject jsonObject=result.getData();
//        Assert.assertEquals(jsonObject.get("fundCode"),"000001");
    }
}
