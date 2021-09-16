package com.fundgroup.backend.utils.messageUtils;


import com.alibaba.fastjson.JSONObject;

public class Message {
    private int status;
    private String message;
    private JSONObject data;

    public Message(int status, String message, JSONObject data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Message(int status, JSONObject data) {
        this.status = status;
        this.data = data;
    }

    public Message(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = new JSONObject();
    }

    public Message(int status){
        this.status = status;
    }

    public Message(){

    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public JSONObject getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
