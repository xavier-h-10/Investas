package com.fundgroup.backend.ControllerTest;

import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.utils.messageUtils.Message;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FundPredictionControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetFundPrediction() {
		Message message = restTemplate.getForObject("/anyone/fund/prediction?code=000689", Message.class);
	}
}
