package com.fundgroup.backend.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.cache.FundAssemblyCache;
import com.fundgroup.backend.dto.FundAssembly;
import com.fundgroup.backend.dto.FundCompeRank;
import com.fundgroup.backend.entity.FundCompeUser;
import com.fundgroup.backend.entity.FundCompeUserPos;
import com.fundgroup.backend.entity.FundCompeUserPosLog;
import com.fundgroup.backend.entity.FundCompetition;
import com.fundgroup.backend.security.UserInfoHelper;
import com.fundgroup.backend.service.FundCompeUserService;
import com.fundgroup.backend.service.FundCompetitionService;
import com.fundgroup.backend.utils.messageUtils.Message;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FundCompetitionController {

	@Autowired
	FundCompeUserService fundCompeUserService;

	@Autowired
	FundAssemblyCache fundAssemblyCache;

	@Autowired
	FundCompetitionService fundCompetitionService;

	@Autowired
	UserInfoHelper userInfoHelper;

	@RequestMapping(value = "/user/competition/createCompetition", method = RequestMethod.POST)
	public Message createCompetition(@RequestBody Map<String, Object> value) {
		Integer result = fundCompetitionService.createCompetition(value);
		if (result == -1) {
			return new Message(-1, "请登录以创建比赛");
		}
		return new Message(0, String.valueOf(result));
	}

	@RequestMapping(value = "/user/competition/getAllCompetition", method = RequestMethod.POST)
	public Message getAllCompetition() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", fundCompetitionService.getAll());
		return new Message(0, "获取比赛列表成功",
				jsonObject);
	}

	@RequestMapping(value = "/admin/competition/queryCompetitionList", method = RequestMethod.POST)
	public Message queryAllCompetitionFromAdmin() {
//    管理员的更高权限：查看创建者的信息
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", fundCompetitionService.getAll());
		return new Message(0, "获取比赛列表成功",
				jsonObject);
	}

	@RequestMapping(value = "/user/competition/removeCompetition", method = RequestMethod.POST)
	public Message deleteCompetitionFromAdmin(@RequestBody Map<String, Integer> value) {
//    管理员的更高权限：删除比赛
		Integer competitionId = value.get("id");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", fundCompetitionService.removeCompetition(competitionId));
		return new Message(0, "删除成功", jsonObject);
	}

	@RequestMapping(value = "/user/competition/getActiveCompetitionsByUserAndFundType", method = RequestMethod.POST)
	public Message getActiveCompetitionsByFundType(@RequestBody Map<String, Object> params) {
		Long userId = userInfoHelper.getUserId();
		Integer fundType = (Integer) params.get("type");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", fundCompetitionService.getActiveCompetitionByUserIdAndFundTypeAndEndDate(userId,
				1 << fundType, LocalDate.now()));
		return new Message(MessageUtil.SUCCESS, "成功", jsonObject);
	}

	@RequestMapping(value = "/user/competition/buyFund", method = RequestMethod.POST)
	public Message buyFund(@RequestBody Map<String, Object> params) {
		Long userId = userInfoHelper.getUserId();
		Integer competitionId = (Integer) params.get("competitionId");
		String fundCode = (String) params.get("fundCode");
		BigDecimal amount;
		try {
			amount = BigDecimal.valueOf((Double) params.get("amount"));
		} catch (ClassCastException ex) {
			amount = BigDecimal.valueOf((Integer) params.get("amount"));
		}
		String ret = fundCompetitionService.buyFund(competitionId, userId, fundCode, amount);
		if (ret == null) {
			return new Message(MessageUtil.SUCCESS, "购买成功");
		} else {
			return new Message(MessageUtil.ERROR, ret);
		}
	}

	@RequestMapping(value = "/user/competition/log/fund/byCodeAndCompeIdAndUserId", method = RequestMethod.POST)
	public Message getFundCompeUserPosLogByCodeAndCompeIdAndUserId(@RequestBody Map<String, Object> params) {
		Long userId = userInfoHelper.getUserId();
		Integer compeId = (Integer) params.get("competitionId");
		String fundCode = (String) params.get("fundCode");
		List<FundCompeUserPosLog> ret = fundCompetitionService.getFundCompeUserPosLogByCodeAndCompeIdAndUserId(fundCode,
				compeId, userId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", ret);
		if (ret == null) {
			return new Message(MessageUtil.SUCCESS, "购买成功");
		} else {
			return new Message(MessageUtil.ERROR, jsonObject);
		}
	}

	@RequestMapping(value = "/user/competition/getFundCompeUserPosByCodeAndCompeId", method = RequestMethod.POST)
	public Message getFundCompeUserPosByCodeAndCompeId(@RequestBody Map<String, Object> params) {
		Long userId = userInfoHelper.getUserId();
		Integer compeId = (Integer) params.get("competitionId");
		String fundCode = (String) params.get("fundCode");
		FundCompeUserPos ret = fundCompetitionService.getFundCompeUserPosByCodeAndCompeIdAndUserId(fundCode,
				compeId, userId);
		return new Message(MessageUtil.SUCCESS, (JSONObject) JSON.toJSON(ret));
	}


	/**
	 * TODO:modify to user only
	 *
	 * @param competitionId
	 * @return
	 */
	@RequestMapping(value = "/user/competition/hold", method = RequestMethod.GET)
	public Message getCompeHold(@RequestParam("competitionId") Integer competitionId) {
		Long userId = userInfoHelper.getUserId();
		LocalDate today = LocalDate.now();
		FundCompeUser fundCompeUser = fundCompeUserService.getFundCompeUserByCompetitionIdAndUserId(competitionId, userId);
		if (fundCompeUser == null) {
			return new Message(MessageUtil.ERROR, "信息不存在");
		}

		List<FundCompeUserPosLog> compeLogList = fundCompeUser.getFundCompeUserPosLogList();
		JSONArray logArray = new JSONArray();
		if (!compeLogList.isEmpty()) {
			for (FundCompeUserPosLog log : compeLogList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("fund_code", log.getFundCode());
				jsonObject.put("date", log.getDate().toString());
				jsonObject.put("amount", log.getAmount());
				logArray.add(jsonObject);
			}
		}


		List<FundCompeUserPos> fundCompeUserPosList = fundCompeUser.getFundCompeUserPosList();
		JSONArray posArray = new JSONArray();
		if (!fundCompeUserPosList.isEmpty()) {
			for (FundCompeUserPos fundCompeUserPos : fundCompeUserPosList) {
				String fundCode = fundCompeUserPos.getFundCode();
				FundAssembly fundAssembly = fundAssemblyCache.getFundAssembly(fundCode);
				Double nav;
				Double oneDayRate;
				if (fundAssembly == null) {
					nav = null;
					oneDayRate = null;
				} else {
					nav = fundAssembly.getNAV();
					oneDayRate = fundAssembly.getLastOneDayRate();
				}
				JSONObject userPos = new JSONObject();
				userPos.put("fund_code", fundCode);
				userPos.put("amount", fundCompeUserPos.getAmount());
				userPos.put("nav", nav);
				userPos.put("one_day_rate", oneDayRate);
				posArray.add(userPos);
			}

		}


		JSONObject userInfo = new JSONObject();
		userInfo.put("surplus_money", fundCompeUser.getSurplusMoney());
		userInfo.put("total_assert", fundCompeUser.getTotalAsset());
		userInfo.put("total_change", fundCompeUser.getTotalChange());
		userInfo.put("is_end", fundCompeUser.getIsEnd());

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("user_info", userInfo);
		jsonObject.put("items", posArray);
		jsonObject.put("log", logArray);
		return new Message(MessageUtil.SUCCESS, jsonObject);
	}

	/**
	 * TODO: modify to admin
	 *
	 * @return
	 */
	@RequestMapping(value = "/anyone/competition/calculation", method = RequestMethod.GET)
	public Message dailyCompeCalculation() {
		List<Integer> failCompetition = fundCompeUserService.updateActiveCompetition();
		if (failCompetition.isEmpty())
			return new Message(MessageUtil.SUCCESS, "success update");
		JSONArray failArray = JSONArray.parseArray(JSON.toJSONString(failCompetition));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fail_competition", failArray);
		return new Message(MessageUtil.ERROR, "update fail", jsonObject);
	}

	/**
	 * TODO: modify to user, add to redis
	 *
	 * @return
	 */
	@RequestMapping(value = "/user/competition/rank", method = RequestMethod.GET)
	public Message getRankByCompeId(@RequestParam("competitionId") Integer competitionId) {
		List<FundCompeRank> fundCompeRankList = fundCompeUserService.getTopRankByCompetitionId(competitionId, 5);
		if (fundCompeRankList.isEmpty())
			return new Message(MessageUtil.ERROR);
		JSONArray array = JSONArray.parseArray(JSON.toJSONString(fundCompeRankList));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rank_list", array);
		return new Message(MessageUtil.SUCCESS, jsonObject);
	}

	/**
	 * TODO: add to redis
	 *
	 * @return
	 */
	@RequestMapping(value = "/user/competition/simple", method = RequestMethod.GET)
	public Message getCompetitionSimpleByCompeId(@RequestParam("competitionId") Integer competitionId) {
		FundCompetition fundCompetition = fundCompetitionService.getFundCompetitionByCompeId(competitionId);
		if (fundCompetition == null) {
			return new Message(MessageUtil.ERROR, "比赛不存在");
		}
		JSONObject competition = new JSONObject();
		competition.put("competition_id", competitionId);
		competition.put("competition_name", fundCompetition.getCompetitionName());
		competition.put("start_date", fundCompetition.getStartDate().toString());
		competition.put("end_date", fundCompetition.getEndDate().toString());

		return new Message(MessageUtil.SUCCESS, competition);
	}

	/**
	 * TODO: add to redis
	 *
	 * @return
	 */
	@RequestMapping(value = "/user/competition/detail", method = RequestMethod.GET)
	public Message getCompetitionDetailByCompeId(@RequestParam("competitionId") Integer competitionId) {
		FundCompetition fundCompetition = fundCompetitionService.getFundCompetitionByCompeId(competitionId);
		if (fundCompetition == null) {
			return new Message(MessageUtil.ERROR, "比赛不存在");
		}
		JSONObject competition = new JSONObject();
		competition.put("competition_id", competitionId);
		competition.put("competition_name", fundCompetition.getCompetitionName());
		competition.put("start_date", fundCompetition.getStartDate().toString());
		competition.put("end_date", fundCompetition.getEndDate().toString());
		competition.put("competition_description", fundCompetition.getCompetitionDescription());
		competition.put("capacity", fundCompetition.getCapacity());
		competition.put("number", fundCompetition.getNumber());
		Vector<Integer> typeVector = fundCompetition.Bigint2vector(fundCompetition.getAllowedFundType());
		competition.put("allowed_fund_type", typeVector);

		return new Message(MessageUtil.SUCCESS, competition);
	}

	/**
	 * TODO: user only
	 *
	 * @return
	 */
	@RequestMapping(value = "/user/competition/active/public/detail", method = RequestMethod.GET)
	public Message getAllActivePublicCompetition() {
		JSONObject list = new JSONObject();
		List<FundCompetition> fundCompetitionList = fundCompetitionService.getAllActivePublicCompetition(LocalDate.now());
		if (fundCompetitionList.isEmpty()) {
			list.put("list", null);
			return new Message(MessageUtil.SUCCESS, list);
		}
		JSONArray jsonArray = new JSONArray();
		for (FundCompetition fundCompetition : fundCompetitionList) {
			JSONObject competition = new JSONObject();
			competition.put("competition_id", fundCompetition.getCompetitionId());
			competition.put("competition_name", fundCompetition.getCompetitionName());
			competition.put("start_date", fundCompetition.getStartDate().toString());
			competition.put("end_date", fundCompetition.getEndDate().toString());
			competition.put("competition_description", fundCompetition.getCompetitionDescription());
			competition.put("capacity", fundCompetition.getCapacity());
			competition.put("number", fundCompetition.getNumber());
			Vector<Integer> typeVector = fundCompetition.Bigint2vector(fundCompetition.getAllowedFundType());
			competition.put("allowed_fund_type", typeVector);
			jsonArray.add(competition);

		}
		list.put("list", jsonArray);
		return new Message(MessageUtil.SUCCESS, list);
	}

	@RequestMapping(value = "/user/competition/active/public/detail/my", method = RequestMethod.GET)
	public Message getAllActivePublicCompetitionByUserId() {
		JSONObject list = new JSONObject();
		Long userId = userInfoHelper.getUserId();
		LocalDate today = LocalDate.now();
		List<FundCompetition> fundCompetitionList = fundCompetitionService.getActivePublicCompetitionByUserId(userId, today);
		if (fundCompetitionList.isEmpty()) {
			list.put("list", null);
			return new Message(MessageUtil.SUCCESS, list);
		}
		JSONArray jsonArray = new JSONArray();
		for (FundCompetition fundCompetition : fundCompetitionList) {
			JSONObject competition = new JSONObject();
			competition.put("competition_id", fundCompetition.getCompetitionId());
			competition.put("competition_name", fundCompetition.getCompetitionName());
			competition.put("start_date", fundCompetition.getStartDate().toString());
			competition.put("end_date", fundCompetition.getEndDate().toString());
			competition.put("competition_description", fundCompetition.getCompetitionDescription());
			competition.put("capacity", fundCompetition.getCapacity());
			competition.put("number", fundCompetition.getNumber());
			Vector<Integer> typeVector = fundCompetition.Bigint2vector(fundCompetition.getAllowedFundType());
			competition.put("allowed_fund_type", typeVector);
			jsonArray.add(competition);

		}
		list.put("list", jsonArray);
		return new Message(MessageUtil.SUCCESS, list);
	}

	/**
	 * TODO:modify to user only and user id
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/user/competition/join", method = RequestMethod.POST)
	public Message joinCompetitionByCompeId(@RequestBody Map<String, Object> params) {
		Long userId = userInfoHelper.getUserId();
		//check arguments
		Integer competitionId = (Integer) params.get("competitionId");
		FundCompeUser fundCompeUser = new FundCompeUser(competitionId, userId, null, null, null, false);
		Integer ret = fundCompeUserService.joinCompetition(fundCompeUser);
		if (ret == -1)
			return new Message(MessageUtil.ERROR, "已加入该比赛，请勿重复添加");
		else if (ret == -2)
			return new Message(MessageUtil.ERROR, "该比赛无法加入");
		else if (ret == -3)
			return new Message(MessageUtil.ERROR, "以达到最大人数上限");
		return new Message(MessageUtil.SUCCESS, "成功加入比赛");

	}

}
