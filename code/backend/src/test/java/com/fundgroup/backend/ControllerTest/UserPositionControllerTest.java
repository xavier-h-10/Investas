package com.fundgroup.backend.ControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.controller.UserPositionController;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
public class UserPositionControllerTest {

	UserPositionController userPositionController;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	void setUserPositionController(UserPositionController userPositionController) {
		this.userPositionController = userPositionController;
	}

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	int randomServerPort;


	@Before
	public void startup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@After
	public void endBy() {
		Map<String, String> mp = new HashMap<>();
		testRestTemplate.postForEntity("/logout", null,
				Message.class, mp);
	}

	//  Test: how to use testRestTemplate
	private void TestLogin() throws Exception {
//    prepare login params
		Map<String, String> mp = new HashMap<>();

		mp.put("username", "user");
		mp.put("password", "123456");
		String para = JSON.toJSONString(mp);

		ResponseEntity<Message> message = testRestTemplate.postForEntity("/doLogin", MediaType.APPLICATION_JSON_VALUE,
				Message.class, para);

		Assert.assertEquals(message.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(message.getBody().getStatus(), -1);
		System.out.println(message.getBody().getMessage());
	}

	@Test
	@WithMockUser(username = "user", password = "123456")
	public void TestSetPositionByMoney() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("fund_code", "000689");
		params.put("sum_amount", "5000");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/position/setByMoney")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(JSONObject.toJSONString(params)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//        print message here
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		JSONObject body = JSONObject.parseObject(result.getResponse().getContentAsString());
		Assert.assertNotNull(body.get("status"));
	}

	@Test
	@WithMockUser(username = "user", password = "123456")
	public void TestSetPositionByAmount() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("fund_code", "000689");
		params.put("amount", "5000");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/position/setByAmount")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(JSONObject.toJSONString(params)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//        print message here
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

	}

	@Test
	@WithMockUser(username = "user", password = "123456")
	public void TestDelPositionByFundCode() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("fundCode", "000689");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/position/del")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(JSONObject.toJSONString(params)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//        print message here
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		JSONObject body = JSONObject.parseObject(result.getResponse().getContentAsString());
//		Assert.assertEquals(MessageUtil.SUCCESS, body.get("status"));

		params.clear();
		params.put("fundCode", "000001");
		result = mockMvc.perform(MockMvcRequestBuilders.post("/user/position/del")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(JSONObject.toJSONString(params)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//        print message here
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		body = JSONObject.parseObject(result.getResponse().getContentAsString());
		Assert.assertEquals(MessageUtil.ERROR, body.get("status"));

	}

	@Test
	public void TestDelPositionByFundCodeWithoutLogin() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("fundCode", "000689");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/position/del")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(JSONObject.toJSONString(params)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//        print message here
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		JSONObject body = JSONObject.parseObject(result.getResponse().getContentAsString());
		Assert.assertEquals(-1, body.get("status"));
	}

	@Test
	@WithMockUser(username = "user", password = "123456")
	public void TestCheckPositionByFundCode() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("fundCode", "000689");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/position/check")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(JSONObject.toJSONString(params)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//        print message here
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		JSONObject body = JSONObject.parseObject(result.getResponse().getContentAsString());
//		Assert.assertEquals(MessageUtil.SUCCESS, body.get("status"));

		params.clear();
		params.put("fundCode", "000001");
		result = mockMvc.perform(MockMvcRequestBuilders.post("/user/position/check")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(JSONObject.toJSONString(params)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//        print message here
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		body = JSONObject.parseObject(result.getResponse().getContentAsString());
		Assert.assertEquals(MessageUtil.ERROR, body.get("status"));

	}

	@Test
	public void TestCheckPositionByFundCodeWithoutLogin() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("fundCode", "000689");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/position/check")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(JSONObject.toJSONString(params)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//        print message here
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		JSONObject body = JSONObject.parseObject(result.getResponse().getContentAsString());
		Assert.assertEquals(-1, body.get("status"));
	}

	@Test
	@WithMockUser(username = "user", password = "123456")
	public void TestGetPositionInfo() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/position/getByUser")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//        print message here
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		JSONObject body = JSONObject.parseObject(result.getResponse().getContentAsString());
		Assert.assertEquals(body.get("status"), MessageUtil.SUCCESS);
		Assert.assertNotNull(body.get("data"));

		JSONObject data = JSONObject.parseObject(body.getString("data"));
//		Assert.assertNotNull(data.get("preDate"));
	}

	@Test
	public void TestGetPositionInfoWithoutLogin() throws Exception {
		MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.post("/user/position/getByUser")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//        print message here
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
	}
}
