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
public class FundArchiveDetailControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetFundArchiveDetail() {
		Message message = restTemplate.getForObject("/anyone/archive/detail?code=000689", Message.class);
		Assert.assertEquals(100, message.getStatus());
		Assert.assertNotNull("data is null.", message.getData());
		JSONObject data = message.getData();
		Assert.assertEquals("000689", data.getString("code"));
		Assert.assertNotNull("custodian is null", data.getString("custodian"));
		Assert.assertNotNull("size is null", data.getString("size"));
		Assert.assertNotNull("descriptionList is null", data.getString("descriptionList"));
		Assert.assertNotNull("name is null", data.getString("name"));
		Assert.assertNotNull("start is null", data.getString("start"));
		Assert.assertNotNull("company is null", data.getString("company"));
	}
}
