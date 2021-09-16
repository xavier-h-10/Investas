package com.fundgroup.backend.ControllerTest;

import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeRankFundControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetHistoryRank() {
		Message message;
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=-1", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNull("list is not null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=0", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=0", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=1&size=3&type=0", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=1", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=2", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=3", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=4", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=5", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=6", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=7", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=8", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=9", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=10", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3&type=11", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNull("list is not null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/history?page=0&size=3000000&type=1", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNull("list is not null", message.getData().getString("list"));
	}

	@Test
	public void testGetPredictionRank() {
		Message message;
		message = restTemplate.getForObject("/anyone/fund/rank/prediction?page=0&size=3&type=0", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNull("list is not null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/prediction?page=0&size=3&type=1", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/prediction?page=0&size=3&type=1", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/prediction?page=1&size=3&type=1", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/prediction?page=0&size=3&type=2", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/prediction?page=0&size=3&type=3", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNotNull("list is null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/prediction?page=0&size=3&type=4", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNull("list is not null", message.getData().getString("list"));
		message = restTemplate.getForObject("/anyone/fund/rank/prediction?page=0&size=3000000&type=1", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
		Assert.assertNull("list is not null", message.getData().getString("list"));
	}
	@Test
	public void testClear() {
		Message message;
		message = restTemplate.getForObject("/anyone/fund/rank/clear", Message.class);
		Assert.assertEquals(MessageUtil.SUCCESS, message.getStatus());
	}
}
