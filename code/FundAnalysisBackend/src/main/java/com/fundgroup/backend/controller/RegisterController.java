package com.fundgroup.backend.controller;

import com.fundgroup.backend.entity.Role;
import com.fundgroup.backend.service.MailService;
import com.fundgroup.backend.service.RegisterService;
import com.fundgroup.backend.service.RoleService;
import com.fundgroup.backend.service.UserAuthorityService;
import com.fundgroup.backend.service.UserService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;

@RestController
public class RegisterController {

  private MailService mailService;
  private RoleService roleService;
  private UserAuthorityService userAuthorityService;
  private RegisterService registerService;
  private UserService userService;

  @Autowired
  void setMailService(MailService mailService) {
    this.mailService = mailService;
  }

  @Autowired
  void setRoleService(RoleService roleService) {
    this.roleService = roleService;
  }

  @Autowired
  void setUserAuthorityService(UserAuthorityService userAuthorityService) {
    this.userAuthorityService = userAuthorityService;
  }

  @Autowired
  void setRegisterService(RegisterService registerService) {
    this.registerService = registerService;
  }

  @Autowired
  void setUserService(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = "/anyone/register/get_verification", method = RequestMethod.POST)
  public Message registerAuth(@RequestBody Map<String, String> params) {
    String email = params.get("email");
    String deviceId = params.get("device_id");
    String code = "";
    System.out.println("email=" + email);
    System.out.println("device_id=" + deviceId);

    // 此处不应该用userAuthorityService的方法，因为是对email查重 20210815
    if (userService.findUserByEmail(email) != null) {
      return new Message(-1, MessageUtil.DUPLICATE_NAME);
    }

    Random random = new Random();
    for (int i = 0; i < 6; i++) {
      int now = random.nextInt(10);
      code = code + now;
    }

    registerService.setAuth(deviceId, code);
    String res = "感谢您注册交我赚——基金分析系统！您的验证码为：" + code + "<br/>";
    res += "此验证码将于10分钟后过期。<br/>";
    res += "------------------------<br/>";
    res += "此致，<br/>" + "交我赚用户团队<br/>";
    mailService.sendHtmlMail(email, "注册验证码", res);

    return new Message(0, "验证码已发送。");
  }

  @RequestMapping("anyone/register/register")
  public Message userRegister(@RequestBody Map<String, String> params) {
    String email = params.get("email");
    String username = params.get("username");
    String password = params.get("password");
    if (email == null || username == null || password == null) {
      return new Message(-1, "注册失败");
    }
    // set role as user default
    List<Role> roles = new ArrayList<Role>();
    Role role = roleService.getRoleById(4);
    roles.add(role);
    System.out.println(email);
    System.out.println(username);
    System.out.println(password);
    int status = userAuthorityService.register(email, username, password, roles);

    Message message = new Message(status);
    message.setMessage("注册成功");
    return message;
  }

  @RequestMapping("/anyone/register/check_verification")
  public Message checkAuth(@RequestBody Map<String, String> params) {
    String deviceId = params.get("device_id"), code = params.get("code");
    if (registerService.checkAuth(deviceId, code)) {
      return new Message(0, "邮箱注册成功。");
    } else {
      return new Message(-1, "验证码错误。");
    }
  }

}
