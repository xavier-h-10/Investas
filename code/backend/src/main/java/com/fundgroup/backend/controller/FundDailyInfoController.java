package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dto.FundDailySimp;
import com.fundgroup.backend.dto.StockDailySimp;
import com.fundgroup.backend.entity.FundDailyInfo;
import com.fundgroup.backend.service.FundDailyInfoService;
import com.fundgroup.backend.service.StockDailyInfoService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FundDailyInfoController {
	@Autowired
	private FundDailyInfoService fundDailyInfoService;

	@Autowired
	private StockDailyInfoService stockDailyInfoService;

	@RequestMapping(value = "/anyone/fund/dailyinfo", method = RequestMethod.GET)
	public Message getFundDailyTimeRange(@RequestParam("fundCode") String fundCode, @RequestParam("timeType") Integer type) {
		long daily_begin=System.currentTimeMillis();//test
		TimeType timeType = TimeType.values()[type];
		List<FundDailySimp> fundDailyInfos = fundDailyInfoService.getFundDailyTimeRange(fundCode, timeType);
		List<StockDailySimp> stockDailySimps = stockDailyInfoService.getStockDailyTimeRange("000300",timeType);
		JSONArray fundDailyArray = new JSONArray();
		JSONArray stockDailyArray = new JSONArray();
		fundDailyArray.addAll(fundDailyInfos);
		stockDailyArray.addAll(stockDailySimps);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dailylist", fundDailyArray);
		jsonObject.put("stockdailylist", stockDailyArray);
		if (fundDailyInfos == null || stockDailySimps==null)
			return new Message(MessageUtil.ERROR);
		System.out.println(fundDailyInfos.size());
		long daily_end=System.currentTimeMillis();//test
		System.out.println("stock:");//test
		System.out.println(daily_end-daily_begin);//test
		return new Message(MessageUtil.SUCCESS, jsonObject);
	}

	@RequestMapping(value = "/anyone/fund/daily/info", method = RequestMethod.GET)
	public Message getFundDailyPage(@RequestParam("fundCode") String fundCode, @RequestParam("page") Integer page) {
		Integer pageSize = 30;
		List<FundDailySimp> fundDailyInfos = fundDailyInfoService.getFundDailyDetailFourPage(fundCode, page, pageSize);
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(fundDailyInfos);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dailylist", jsonArray);
		if (fundDailyInfos == null)
			return new Message(MessageUtil.ERROR);
		System.out.println(fundDailyInfos.size());
		return new Message(MessageUtil.SUCCESS, jsonObject);
	}

	@RequestMapping(value = "/anyone/fund/daily/info/interpolation", method = RequestMethod.POST)
	public Message missingDateInterpolation()
	{
		fundDailyInfoService.missingDateInterpolation();
		return new Message(MessageUtil.SUCCESS);
	}
}
