package com.fundgroup.backend.SpringSecurityTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fundgroup.backend.controller.UserPositionController;
import com.fundgroup.backend.utils.verifyCodeUtils.VerifyCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
public class VerifyCodeTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @Before
  public void startup() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
  }

  @Test
  public void TestGetVerifyCode() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/anyone/login_helper/VCode"))
        .andExpect(status().isOk())
//        print message here
        .andDo(MockMvcResultHandlers.print())
        .andReturn();


  }
}
