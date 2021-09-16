package com.fundgroup.backend.utils.messageUtils;


import com.alibaba.fastjson.JSONObject;

public class MessageUtil {
    public static final String SUCCESS_MSG = "请求成功。";
    public static final String LOGIN_ERROR_MSG = "密码或用户名有误，请重新输入。";
    public static final String LOGIN_SUCCESS_MSG = "登陆成功。";
    public static final String ALREADY_LOGIN_MSG = "用户已经登陆。";
    public static final String NOT_LOGIN_MSG = "用户未登陆。";
    public static final String ILLEGAL_REQUEST = "非法请求。";
    public static final String LOGOUT_SUCCESS_MSG = "退出登录成功。";
    public static final String LOGOUT_ERROR_MSG = "退出登录失败。";
    public static final String LOGIN_FORBIDDEN = "您的账户被禁用。请联系管理员。";
    public static final String DUPLICATE_NAME = "此邮箱已经注册。";
    public static final String NOT_FOUND_USER = "未找到该用户。";

    public static final int LOGIN_ERROR_CODE = -1;
    public static final int LOGIN_FORBIDDEN_CODE = -2;
    public static final int LOGIN_SUCCESS_CODE = 1;
    public static final int NOT_LOGIN_CODE = -2;
    public static final int ALREADY_LOGIN_CODE = 0;
    public static final int LOGOUT_SUCCESS_CODE = 2;
    public static final int LOGOUT_ERROR_CODE = -3;

    public static final int SUCCESS = 100;
    public static final int ERROR = -100;
    public static final int INVALID_ARGUMENT = -4;

    public static Message createMessage(int statusCode, String message) {
        return new Message(statusCode, message);
    }

    public static Message createMessage(int statusCode, String message, JSONObject data) {
        return new Message(statusCode, message, data);
    }
}
