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
public class FundDailyInfoControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetFundDailyTimeRange() {
		Message message = restTemplate.getForObject("/anyone/fund/dailyinfo?fundCode=000689&timeType=1", Message.class);
		message = restTemplate.getForObject("/anyone/fund/dailyinfo?fundCode=000689&timeType=-1", Message.class);
	}

	@Test
	public void testGetFundDailyPage() {
		Message message = restTemplate.getForObject("/anyone/fund/daily/info?fundCode=000689&page=2", Message.class);
	}
}
