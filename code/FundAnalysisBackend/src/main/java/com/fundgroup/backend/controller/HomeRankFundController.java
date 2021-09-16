package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dao.HomeRankFundDao;
import com.fundgroup.backend.service.HomeRankFundService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRankFundController {

	@Autowired
	HomeRankFundService homeRankFundService;

	@RequestMapping(value = "/anyone/fund/rank/history", method = RequestMethod.GET)
	public Message getHistoryRank(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
								  @RequestParam("type") Integer type) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", homeRankFundService.getHistoryTop(page, size, type));
		return new Message(MessageUtil.SUCCESS, jsonObject);
	}

	@RequestMapping(value = "/anyone/fund/rank/prediction", method = RequestMethod.GET)
	public Message getPredictionRank(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
									 @RequestParam("type") Integer type) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", homeRankFundService.getPredictionTop(page, size, type));
		return new Message(MessageUtil.SUCCESS, jsonObject);
	}

	@RequestMapping(value = "/anyone/fund/rank/clear")
	public Message clearCache() {
		homeRankFundService.clearCache();
		return new Message(MessageUtil.SUCCESS);
	}
}
