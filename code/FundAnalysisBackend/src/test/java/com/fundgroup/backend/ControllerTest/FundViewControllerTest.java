package com.fundgroup.backend.ControllerTest;

import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.utils.messageUtils.Message;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FundViewControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetFundView() {
		Message message = restTemplate.getForObject("/anyone/fund/view?code=000689", Message.class);
		Assert.assertEquals(100, message.getStatus());
		Assert.assertNotNull("data is null.", message.getData());
		JSONObject data = message.getData();
		Assert.assertNotNull("historyPerformance is null", data.get("historyPerformance"));
		JSONObject archive = data.getJSONObject("archive");
		Assert.assertEquals("000689", archive.getString("code"));
		Assert.assertNotNull("size is null.", archive.get("size"));
//		Assert.assertNotNull("stockList is null.", archive.get("stockList"));
		Assert.assertNotNull("managerList is null.", archive.get("managerList"));
		Assert.assertNotNull("start is null.", archive.get("start"));
	}
}
