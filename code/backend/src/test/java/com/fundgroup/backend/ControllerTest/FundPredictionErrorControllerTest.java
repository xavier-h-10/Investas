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
public class FundPredictionErrorControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getErrorMonitorData() {
		Message message = restTemplate.getForObject("/anyone/predictionError/getErrorMonitorData", Message.class);
	}

	@Test
	public void getErrorMax() {
		Message message = restTemplate.getForObject("/anyone/predictionError/getErrorMax", Message.class);
	}

	@Test
	public void getErrorMin() {
		Message message = restTemplate.getForObject("/anyone/predictionError/getErrorMin", Message.class);
	}

	@Test
	public void getErrorByCode() {
		Message message = restTemplate.getForObject("/anyone/predictionError/getErrorByCode?fundCode=000689",
				Message.class);
	}

	@Test
	public void getPredictionFrontendView() {
		Message message = restTemplate.getForObject("/anyone/predictionFundView/getPredictionFrontendView" +
				"?fundCode=000689", Message.class);
	}
}
