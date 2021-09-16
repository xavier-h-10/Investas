package com.fundgroup.backend.SpringSecurityTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.entity.Role;
import com.fundgroup.backend.security.UserInfoHelper;
import com.fundgroup.backend.service.MailService;
import com.fundgroup.backend.service.RegisterService;
import com.fundgroup.backend.service.RoleService;
import com.fundgroup.backend.service.UserAuthorityService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.CsrfRequestPostProcessor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
public class SecurityTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private UserInfoHelper userInfoHelper;

  @Autowired
  private RoleService roleService;

  @Autowired
  private RegisterService registerService;

  @Autowired
  private MailService mailService;

  @Autowired
  private UserAuthorityService userAuthorityService;

  @Before
  public void startup() throws Exception {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .apply(springSecurity())
        .build();
  }

  @Autowired
  private TestRestTemplate testRestTemplate;

  @LocalServerPort
  int randomServerPort;

  @Test
  public void TestBasicSecurityConfig() throws Exception{
    userInfoHelper.getUserId();
    userInfoHelper.getUserName();
    userAuthorityService.hasSameUserName("user@sjtu.edu.cn");


    userAuthorityService.loadUserByUsername("user");
    Assert.assertThrows(UsernameNotFoundException.class, () -> userAuthorityService.loadUserByUsername("invalid"));

    List<Role> roles = new ArrayList<>();
    Role role = roleService.getRoleById(1);
    roles.add(role);
    userAuthorityService.register("test@jwz.edu.cn",
        "test", "123456", roles);

    registerService.setAuth("123", "567");
    registerService.checkAuth("123", "567");
  }

  @Test
  public void TestMailService(){
    String mailbox = "1179658820@qq.com";
    String subject = "test";
    String text = "test";
    mailService.sendHtmlMail(mailbox, subject, text);
    mailService.sendHtmlMail(mailbox, subject, text);
  }

  @Test
  public void TestIllegalVisit() throws Exception {

//    First Test illegal visit without login
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/position/getByUser")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
//        print message here
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

    Assert.assertNull(userInfoHelper.getUserName());
    Assert.assertEquals(userInfoHelper.getUserId().longValue(), -1L);


    JSONObject body = JSONObject.parseObject(result.getResponse().getContentAsString());
    Assert.assertEquals(body.get("status"), MessageUtil.LOGIN_ERROR_CODE);

//    could not appear any data from backend
    Assert.assertNull(body.get("data"));

//    without enough level of Roles
    result = mockMvc.perform(MockMvcRequestBuilders.get("/superAdmin/hello")
        .with(user("admin").password("123456")))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

    body = JSONObject.parseObject(result.getResponse().getContentAsString());
    Assert.assertEquals(body.get("status"), MessageUtil.LOGIN_ERROR_CODE);

//    could not appear any data from backend
    Assert.assertNull(body.get("data"));

  }

  @Test
  @WithMockUser(username = "superAdmin", password = "123456", roles = {"superAdmin"}, value = "superAdmin")
  public void TestHighLevelVisit() throws Exception {

    Assert.assertEquals(userInfoHelper.getUserId().longValue(), 1L);
    Assert.assertEquals(userInfoHelper.getUserName(), "superAdmin");
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/hello").with(csrf()))
        .andExpect(status().isOk())
//        print message here
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

//    Assert.assertEquals(result.getResponse().getContentAsString(), "hello");
  }

  @Test
  public void TestLoginAndLogout() throws Exception{
//    Right username and password
    Map<String, String> params = new HashMap<>();
    params.put("username", "user");
    params.put("password", "123456");
    String paras = JSON.toJSONString(params);

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/doLogin")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(paras))
        .andExpect(status().isOk())
//        print message here
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

    JSONObject body = JSONObject.parseObject(result.getResponse().getContentAsString());
    Assert.assertNotNull(body.get("data"));
    Assert.assertEquals(body.get("status"), MessageUtil.ALREADY_LOGIN_CODE);

//    Logout test
    result = mockMvc.perform(MockMvcRequestBuilders.post("/logout")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(""))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

//    wrong username and password
    params.put("username", "invalid");
    params.put("password", "invalid");
    paras = JSON.toJSONString(params);

    result = mockMvc.perform(MockMvcRequestBuilders.post("/doLogin")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(paras))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

   body = JSONObject.parseObject(result.getResponse().getContentAsString());
    Assert.assertNotNull(body.get("data"));
    Assert.assertEquals(body.get("status"), MessageUtil.LOGIN_ERROR_CODE);


//    Map<String, String> mp = new HashMap<>();
//    mp.put("username", "user");
//    mp.put("password", "123456");
//    String para = JSON.toJSONString(mp);
//
//    Message message = testRestTemplate.postForObject
//        ("http://localhost:" + randomServerPort + "/doLogin",
//            MediaType.APPLICATION_JSON_VALUE,
//            Message.class, para);
//
//    Assert.assertEquals(message.getStatus(), -1);
//    System.out.println(message.getMessage());
  }

  @Test
  public void TestLoginFilterWithException() throws Exception{
    //    without username and password
    Map<String, String> params = new HashMap<>();
    params.put("invalid", "user");
    String paras = JSON.toJSONString(params);

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/doLogin")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(paras))
        .andExpect(status().isOk())
//        print message here
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

    JSONObject body = JSONObject.parseObject(result.getResponse().getContentAsString());
    Assert.assertNotNull(body.get("data"));
    Assert.assertEquals(body.get("status"), MessageUtil.LOGIN_ERROR_CODE);

//    think of IO Exception
    List<String> invalid = new ArrayList<>();
    invalid.add("invalid");
    paras = JSON.toJSONString(invalid);

    result = mockMvc.perform(MockMvcRequestBuilders.post("/doLogin")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(paras))
        .andExpect(status().isOk())
//        print message here
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

//    Test not post
    result = mockMvc.perform(MockMvcRequestBuilders.get("/doLogin")
            .with(csrf()))
        .andExpect(status().isOk())
//        print message here
        .andDo(MockMvcResultHandlers.print())
        .andReturn();


  }

  @Test
  public void TestFormLogin() throws Exception {

    MvcResult result = mockMvc.perform(formLogin("/doLogin")
                .acceptMediaType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .user("username", "user")
                .password("password", "123456")
            )
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

    JSONObject body = JSONObject.parseObject(result.getResponse().getContentAsString());
    Assert.assertNotNull(body.get("data"));
    Assert.assertEquals(body.get("status"), MessageUtil.ALREADY_LOGIN_CODE);
  }
}
