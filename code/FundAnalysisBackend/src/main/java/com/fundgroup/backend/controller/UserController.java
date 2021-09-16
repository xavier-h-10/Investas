package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.entity.User;
import com.fundgroup.backend.repository.UserRepository;
import com.fundgroup.backend.service.MailService;
import com.fundgroup.backend.service.RegisterService;
import com.fundgroup.backend.service.RoleService;
import com.fundgroup.backend.service.UserAuthorityService;
import com.fundgroup.backend.service.UserService;
import com.fundgroup.backend.utils.FileUploadUtil;
import com.fundgroup.backend.utils.ImageUtil;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.entity.UserAuthority;

import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import com.fundgroup.backend.utils.verifyCodeUtils.VerifyCode;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


@RestController
public class UserController {

    UserAuthorityService userAuthorityService;

    RoleService roleService;

    UserService userService;

    RegisterService registerService;

    MailService mailService;

    UserRepository userRepository;

    @Autowired
    void setUserAuthorityService(UserAuthorityService userAuthorityService) {
        this.userAuthorityService = userAuthorityService;
    }

    @Autowired
    void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Autowired
    void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //  如果用户登录，可以访问这个接口获取基本的个人信息
    @RequestMapping(value = "user/getUserInfo", method = RequestMethod.POST)
    public Message getUserInfo() {
        Long userId = userService.getUserId();
        if (userId == -1L) {
            return new Message(-1, "未识别身份信息");
        }
        User userInfo = userService.findUserByUserId(userId);
        UserAuthority userAuthority = userAuthorityService.getById(userId);
        System.out.println(userInfo);
        JSONObject map = new JSONObject();

        map.put("nickname", userInfo.getNickname());
        map.put("email", userInfo.getEmail());
        map.put("riskLevel", userInfo.getRiskLevel());
        map.put("status", userInfo.getStatus());
        map.put("userId", userInfo.getUserId());
        map.put("auth", userAuthority.getAuthorities().toArray()[0]);
        map.put("introduction",userInfo.getIntroduction());

        String url = userInfo.getAvatarUrl();
        if(url!=null)
        {
            File file = new File(url);

            if (file.exists()) {
                map.put("avatar", ImageUtil.getImgStr(url));
            }
        }

        return new Message(0, "用户信息获取成功", map);
    }

    // 上传图片
    // 参考资料: https://www.codejava.net/frameworks/spring-boot/spring-boot-file-upload-tutorial
    @RequestMapping(value = "user/upload", method = RequestMethod.POST)
    public Message upload(@RequestParam("image") MultipartFile file) {
        String fileName = file.getOriginalFilename(); // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String filePath = "avatar/" + formatter.format(date) + "/";
        fileName = UUID.randomUUID() + suffixName; // 新文件名


        Long userId = userService.getUserId();
        if (userId == -1L) {
            return new Message(-1, "未识别身份信息");
        }
        try {
            FileUploadUtil.saveFile(filePath, fileName, file);
            User userInfo = userService.findUserByUserId(userId);
            userInfo.setAvatarUrl(filePath + fileName);
            userRepository.save(userInfo);
            System.out.println(userInfo);
            return new Message(0, "上传图片成功");
        } catch (IOException e) {
            e.printStackTrace();
            return new Message(-1, "上传图片失败");
        }
    }


    @RequestMapping("anyone/user/isAuth")
    public Message isAuth() {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();//获取用户信息

//      检查用户是否登录
            Message message = new Message();
            if (authentication.isAuthenticated()) {
                message.setStatus(0);
                message.setMessage("已授权用户");
            } else {
                return new Message(-1, "用户未登录");
            }

            //用户的所有权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            JSONObject jsonObject = new JSONObject();
            for (GrantedAuthority authority : authorities) {
                jsonObject.put("role", authority.getAuthority());
            }

            message.setData(jsonObject);
            return message;
        } else {
//      返回某个值保证前端得知相关信息登录，这里用utils::message
            return new Message(-1, "用户未登录");
        }
    }

    //  Verify code
    @GetMapping("anyone/login_helper/VCode")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        String text = vc.getText();
        HttpSession session = request.getSession();
        session.setAttribute("index_code", text);
        VerifyCode.output(image, response.getOutputStream());
    }

    @RequestMapping(value = "user/updateUserInfo", method = RequestMethod.POST)
    public Message updateUserInfo(@RequestBody Map<String, String> params) {
        System.out.println(params);
        userService.updateUserInfo(params);
        return new Message(0, "用户信息修改成功!");
    }

    // TODO: change access
    @RequestMapping(value = "/user/managerUser/getFullUserInfo", method = RequestMethod.POST)
    public Message getFullUserInfo() {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("list", userService.getFullUserInfo("user"));
      return new Message(0, "获取用户信息成功", jsonObject);
    }

    @RequestMapping(value = "/user/currentUserInfo", method = RequestMethod.POST)
    public Message getSelfFullUserInfo() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("user", userService.getSelfInfo());
    return new Message(0, "获取个人信息成功", jsonObject);
  }

    @RequestMapping(value = "/user/managerUser/getFullAdminInfo", method = RequestMethod.POST)
    public Message getFullAdminInfo() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("list", userService.getFullUserInfo("admin"));
    return new Message(0, "获取管理员信息成功", jsonObject);
  }

    @RequestMapping(value = "/user/changeUserInfo", method = RequestMethod.POST)
    public Message changeUserInfo(@RequestBody JSONObject param) {

        String introduction = param.getString("introduction");
        String nickname = param.getString("nickname");
        Long userId = param.getLong("userId");

        if(userService.changeUserInfo(userId, introduction, nickname)) {
            return new Message(0, "修改个人信息成功");
        }
        else{
            return new Message(-1, "修改个人信息出现错误");
        }
    }

    @RequestMapping(value = "/user/upgradeToSuperUser", method = RequestMethod.POST)
    public Message upgradeToSuperUser(@RequestBody JSONObject param) {

        Long userId = param.getLong("userId");

        if(userService.upgradeToSuperUser(userId)) {
            return new Message(0, "修改个人信息成功");
        }
        else{
            return new Message(-1, "修改个人信息出现错误");
        }
    }


    //三个接口，负责找回密码 20210910
    @RequestMapping(value = "/anyone/forget/getVerification", method = RequestMethod.POST)
    public Message getVerification(@RequestBody Map<String, String> params) {
        System.out.println("getVerification called");
        String email = params.get("email");
        String deviceId = params.get("device_id");
        String code = "";
        System.out.println("email=" + email);
        System.out.println("device_id=" + deviceId);

        if (userService.findUserByEmail(email) == null) {
            return new Message(-1, MessageUtil.NOT_FOUND_USER);
        }

        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int now = random.nextInt(10);
            code = code + now;
        }

        registerService.setAuth(deviceId, code);
        registerService.setAuth(email, code);
        String res = "您正在找回密码。您的验证码为：" + code + "<br/>";
        res += "此验证码将于10分钟后过期。<br/>";
        res += "我们不会以任何方式索要您的验证码，请勿将验证码信息告知他人。<br/>";
        res += "------------------------<br/>";
        res += "此致，<br/>" + "交我赚用户团队<br/>";
        System.out.println("ready to send mail");
        mailService.sendHtmlMail(email, "验证码", res);

        return new Message(0, "验证码已发送。");
    }

    @RequestMapping(value = "anyone/forget/checkAuth", method= RequestMethod.POST)
    public Message checkAuth(@RequestBody Map<String, String> params) {
        String deviceId = params.get("device_id"), code = params.get("code");
        if (registerService.checkAuth(deviceId, code)) {
            return new Message(MessageUtil.SUCCESS,"验证码正确");
        } else {
            return new Message(-1, "验证码错误。");
        }
    }

    @RequestMapping(value = "anyone/forget/changePassword", method = RequestMethod.POST)
    public Message changePassword(@RequestBody Map<String, String> params) {
        String email = params.get("email");
        String password = params.get("password");
        userService.changePassword(email, password);
        return new Message(MessageUtil.SUCCESS, "密码修改成功");
    }
    @RequestMapping(value = "/user/managerUser/deleteUser", method = RequestMethod.POST)
    public Message deleteUser(@RequestBody JSONObject param) {
        Long userId = param.getLong("userId");

        userService.deleteUser(userId);

        return new Message(0, "禁用用户成功");
    }

    @RequestMapping(value = "/user/managerUser/restoreUser", method = RequestMethod.POST)
    public Message restoreUser(@RequestBody JSONObject param) {
        Long userId = param.getLong("userId");

        userService.restoreUser(userId);

        return new Message(0, "解禁用户成功");
    }


}

