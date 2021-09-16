package com.fundgroup.backend;

import com.fundgroup.backend.controller.UserController;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

  UserController userController;

  @Autowired
  void setLoginController(UserController userController) {
    this.userController = userController;
  }

  @BeforeEach
  void setUp() {
    System.out.println("setUp called");
  }

  @AfterEach
  void tearDown() {
    System.out.println("tearDown called");
  }

  @Test
  void contextLoads() {
    //总测试
    testLoginController();
  }


  @Test
  void testLoginController() {
    Map<String, String> auth1 = new HashMap<>(); // correct
    Map<String, String> auth2 = new HashMap<>(); // wrong
    auth1.put("username", "admin");
    auth1.put("password", "123456");
    auth2.put("username", "admin");
    auth2.put("password", "1234567");
    // loginController.login(auth1);
    // loginController.login(auth2);
    // 此处只运行了一遍代码，如果要验证结果是否一致，应采用assertEquals or assertArrayEquals
    // e.g. assertEquals(return_message_auth1, "correct");
  }

}
