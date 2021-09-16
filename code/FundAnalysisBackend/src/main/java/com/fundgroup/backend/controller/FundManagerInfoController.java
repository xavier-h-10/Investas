package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dto.ManagerInfo;
import com.fundgroup.backend.service.FundManagerInfoService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FundManagerInfoController {
	@Autowired
	FundManagerInfoService fundManagerInfoService;

	@RequestMapping(value = "/anyone/manager", method = RequestMethod.GET)
	public Message getManagerInfo(@RequestParam("id") String id) {
		ManagerInfo ret = fundManagerInfoService.getFundManagerInfoById(id);
		if (ret == null) {
			return new Message(MessageUtil.ERROR, "经理不存在");
		} else {
			return new Message(MessageUtil.SUCCESS, (JSONObject) JSON.toJSON(ret));
		}
	}
}
