package com.fundgroup.backend.ControllerTest;

import com.alibaba.fastjson.JSONArray;
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
public class FundManagerInfoControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetManagerInfoNotNull() {
		Message message = restTemplate.getForObject("/anyone/manager?id=30707945", Message.class);
		Assert.assertEquals(100, message.getStatus());
		Assert.assertNotNull("data is null.", message.getData());
		JSONObject data = message.getData();
		Assert.assertEquals("30707945", data.getString("id"));
		Assert.assertEquals("崔宸龙", data.getString("name"));
		Assert.assertNotNull("start is null", data.getString("start"));
		Assert.assertNotNull("description is null", data.getString("description"));
		Assert.assertNotNull("url is null", data.getString("url"));
		Assert.assertNotNull("fundList is null", data.getString("fundList"));
	}

	@Test
	public void testGetManagerInfoNull() {
		Message message = restTemplate.getForObject("/anyone/manager?id=00000001", Message.class);
		Assert.assertEquals(-100, message.getStatus());
		Assert.assertEquals(new JSONObject(), message.getData());
	}
}
