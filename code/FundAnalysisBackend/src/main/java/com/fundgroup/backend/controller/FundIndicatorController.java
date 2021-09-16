//基于规则的基金数据 20210828

package com.fundgroup.backend.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dto.HomeFundIndicator;
import com.fundgroup.backend.entity.FundIndicator;
import com.fundgroup.backend.service.FundIndicatorService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;

@RestController
public class FundIndicatorController {

	private FundIndicatorService fundIndicatorService;

	@Autowired
	void setFundIndicatorService(FundIndicatorService fundIndicatorService) {
		this.fundIndicatorService = fundIndicatorService;
	}

	//测试端口
	@RequestMapping(value = "anyone/fetch_indicator", method = RequestMethod.GET)
	public void fetchFundIndicator() {
		fundIndicatorService.fetchFundIndicator();
	}

	//测试端口
	@RequestMapping(value = "anyone/process_data", method = RequestMethod.GET)
	public void processData() {
		fundIndicatorService.processData();
	}

	@RequestMapping(value = "anyone/getFundIndicator", method = RequestMethod.GET)
	public Message getFundIndicatorByCode(@RequestParam("fundCode") String fundCode) {
		JSONArray result = fundIndicatorService.getFundIndicatorByCode(fundCode);
		return new Message(MessageUtil.SUCCESS, result.toJSONString());
	}

	//给三个指标的数字
	@RequestMapping(value = "anyone/getFundIndicatorNumber", method = RequestMethod.GET)
	public Message getFundIndicatorNumber(@RequestParam("fundCode") String fundCode) {
		List<Integer> result = fundIndicatorService.getFundIndicatorNumber(fundCode);
		return new Message(MessageUtil.SUCCESS, JSON.toJSONString(result));
	}

	//给三个指标的数字
	@RequestMapping(value = "anyone/fund/indicator/rank", method = RequestMethod.GET)
	public Message getHomeFundIndicator(@RequestParam("sharp") Integer sharp, @RequestParam("maxRet") Integer maxRet,
										@RequestParam("stdDev") Integer stdDev, @RequestParam("profit") Integer profit,
										@RequestParam("page")Integer page, @RequestParam("size")Integer size) {
		List<HomeFundIndicator> ret =
				fundIndicatorService.getHomeFundIndicator(sharp, maxRet, stdDev, profit, page, size);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", ret);
		return new Message(MessageUtil.SUCCESS, jsonObject);
	}
}
