package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dto.ScheduleRequest;
import com.fundgroup.backend.entity.UserAuthority;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dto.FundDailySimp;
import com.fundgroup.backend.dto.FundEstimateSimp;
import com.fundgroup.backend.dto.PositionInfo;
import com.fundgroup.backend.dto.PositionInfoFund;
import com.fundgroup.backend.entity.UserPosition;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import com.fundgroup.backend.repository.FundDailyInfoRepository;
import com.fundgroup.backend.repository.FundEstimateRepository;
import com.fundgroup.backend.repository.UserPositionRepository;
import com.fundgroup.backend.security.UserInfoHelper;
import com.fundgroup.backend.service.UserPositionService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fundgroup.backend.utils.messageUtils.MessageUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserPositionController {

	@Autowired
	UserPositionRepository userPositionRepository;
	@Autowired
	FundEstimateRepository fundEstimateRepository;
	@Autowired
	FundBasicInfoRepository fundBasicInfoRepository;
	@Autowired
	FundDailyInfoRepository fundDailyInfoRepository;

	UserPositionService userPositionService;

	UserInfoHelper userInfoHelper;

	@Autowired
	void setUserPositionService(UserPositionService userPositionService) {
		this.userPositionService = userPositionService;
	}

	@Autowired
	void setUserInfoHelper(UserInfoHelper userInfoHelper) {
		this.userInfoHelper = userInfoHelper;
	}

	@RequestMapping(value = "/user/position/setByMoney", method = RequestMethod.POST)
	public Message setPositionByMoney(@RequestBody Map<String, String> params) {
		String fundCode = params.get("fund_code");
		Double sumAmount = Double.valueOf(params.get("sum_amount"));

		return new Message(userPositionService.setPositionByMoney(userInfoHelper.getUserId(), fundCode, sumAmount));
	}

	@RequestMapping(value = "/user/position/setByAmount", method = RequestMethod.POST)
	public Message setPositionByAmount(@RequestBody Map<String, String> params) {
		String fundCode = params.get("fund_code");
		Double amount = Double.valueOf(params.get("amount"));

		return new Message(userPositionService.setPositionByAmount(userInfoHelper.getUserId(), fundCode, amount));
	}

	// @RequestMapping(value = "/user/position/getByUser", method =
	// RequestMethod.POST)
	// public Message getPosition() {
	// Long userId = userInfoHelper.getUserId();
	// if (userId == -1) {
	// return new Message(-1);
	// } else {
	// List<UserPosition> userPositionList =
	// userPositionService.getPositions(userId);
	// JSONObject jsonObject = (JSONObject) JSONObject.toJSON(userPositionList);
	// return new Message(0, "success", jsonObject);
	// }
	// }

	private Double calcChange(List<FundDailySimp> daily) {
		Double curNAV = daily.get(0).getNAV();
		Double preNAV = daily.get(1).getNAV();
		return (curNAV / preNAV - 1) * 100;
	}

	@RequestMapping(value = "/user/position/getByUser", method = RequestMethod.POST)
	public Message getPositionInfo() {
		Long userId = userInfoHelper.getUserId();
		LocalDate cur, pre;
		if (userId == -1) {
			return new Message(-1, MessageUtil.ILLEGAL_REQUEST, null);
		} else {
			PositionInfo positionInfo = new PositionInfo();
			List<PositionInfoFund> fundList = userPositionRepository.getFundByUserId(userId);
			for (PositionInfoFund fund : fundList) {
				String code = fund.getCode();
				List<FundEstimateSimp> fundEstimateSimp = fundEstimateRepository.getFundEstimateRecent(code,
						PageRequest.of(0, 1));
				fund.setEstimate(fundEstimateSimp.get(0));
				fund.setName(fundBasicInfoRepository.getFundNameByFundCode(code));
				List<FundDailySimp> daily = fundDailyInfoRepository.getFundDailyDetailFourByCode(code,
						PageRequest.of(0, 2));
				fund.setNav(daily.get(0).getNAV());
				fund.setNavChange(calcChange(daily));
				cur = daily.get(0).getUpdateDate();
				pre = daily.get(1).getUpdateDate();
				positionInfo.setCurDate(cur);
				positionInfo.setPreDate(pre);
			}
			positionInfo.setFundList(fundList);
			return new Message(MessageUtil.SUCCESS, "success", (JSONObject) JSON.toJSON(positionInfo));
		}
	}

	@RequestMapping(value = "/user/position/del", method = RequestMethod.POST)
	public Message delPositionByFundCode(@RequestBody Map<String, String> params) {
		System.out.println("delPositionByFundCode");
		Long userId = userInfoHelper.getUserId();
		String fundCode = params.get("fundCode");
		if (userId == -1) {
			return new Message(-1, MessageUtil.ILLEGAL_REQUEST);
		} else if (userPositionService.delUserPositionByUserIdAndFundCode(userId, fundCode) == -1) {
			return new Message(MessageUtil.ERROR, "目标不存在");
		} else {
			return new Message(MessageUtil.SUCCESS, "success");
		}
	}

	@RequestMapping(value = "/user/position/check", method = RequestMethod.POST)
	public Message checkPositionByFundCode(@RequestBody Map<String, String> params) {
		System.out.println("checkPositionByFundCode");
		Long userId = userInfoHelper.getUserId();
		String fundCode = params.get("fundCode");
		JSONObject jsonObject = new JSONObject();
		if (userId == -1) {
			return new Message(-1, MessageUtil.ILLEGAL_REQUEST);
		} else if (userPositionService.getUserPositionByUserIdAndFundCode(userId, fundCode) == null) {
			jsonObject.put("exist", 0);
			return new Message(MessageUtil.ERROR, "目标不存在", jsonObject);
		} else {
			jsonObject.put("exist", 1);
			return new Message(MessageUtil.SUCCESS, "目标存在", jsonObject);
		}
	}

}
