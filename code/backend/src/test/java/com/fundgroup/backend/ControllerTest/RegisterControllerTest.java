package com.fundgroup.backend.ControllerTest;

import com.fundgroup.backend.controller.RegisterController;
import com.fundgroup.backend.service.RegisterService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterControllerTest {

  @Autowired
  RegisterController registerController;

  @Autowired
  RegisterService registerService;

  @BeforeEach
  public void BeforeEach() {
    System.out.println("BeforeEach called");
  }

  @AfterEach
  public void AfterEach() {
    System.out.println("AfterEach called");
  }

  @Test
  public void wrapRegisterControllerTest() {
    testRegisterAuth();
    testCheckAuth();
    testUserRegister();
  }

  @Test
  public void testRegisterAuth() {
    Map<String, String> params = new HashMap<>();

    //验证邮箱重名情况
    params.put("email", "13917078913@163.com");
    params.put("device_id", "qianGod");
    Message result = registerController.registerAuth(params);
    Assert.assertEquals(result.getStatus(), -1);
    Assert.assertEquals(result.getMessage(), MessageUtil.DUPLICATE_NAME);

    //验证无法发送邮件情况,此处status=500
//    params.put("email","111");
//    result=registerController.registerAuth(params);
//    Assert.assertEquals(result.getStatus(),500);

    //验证正常发送邮件情况
    params.put("email", "xtommy@sjtu.edu.cn");
    result = registerController.registerAuth(params);
    Assert.assertEquals(result.getStatus(), 0);
  }

  @Test
  public void testCheckAuth() {
    Map<String, String> params = new HashMap<>();
    String deviceId = RandomStringUtils.randomAlphanumeric(10);
    Integer code = (int) ((Math.random() * 9 + 1) * 100000);
    registerService.setAuth(deviceId, String.valueOf(code));
    params.put("device_id", deviceId);
    params.put("code", String.valueOf(code));
    Message result = registerController.checkAuth(params);
    Assert.assertEquals(result.getStatus(), 0);

    params.put("code", String.valueOf(code + 1));
    result = registerController.checkAuth(params);
    Assert.assertEquals(result.getStatus(), -1);

    params.put("code", String.valueOf(code - 1));
    result = registerController.checkAuth(params);
    Assert.assertEquals(result.getStatus(), -1);
  }

  @Test
  public void testUserRegister() {
    Map<String, String> params = new HashMap<>();
    String str = "test_" + RandomStringUtils.randomAlphanumeric(10);
    String email = str + "@test.com";
    params.put("email", email);
    Message result = registerController.userRegister(params);
    Assert.assertEquals(result.getStatus(), -1);

    params.put("username", str);
    result = registerController.userRegister(params);
    Assert.assertEquals(result.getStatus(), -1);

    params.put("password", "123456");
    result = registerController.userRegister(params);
    Assert.assertEquals(result.getStatus(), 0);
  }


}
