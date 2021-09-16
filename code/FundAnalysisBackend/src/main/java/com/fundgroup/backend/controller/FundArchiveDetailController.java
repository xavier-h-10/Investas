package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dto.FundArchiveDetail;
import com.fundgroup.backend.dto.FundDescription;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import com.fundgroup.backend.repository.FundManagerInfoRepository;
import com.fundgroup.backend.repository.FundMessageRepository;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FundArchiveDetailController {
	@Autowired
	FundBasicInfoRepository fundBasicInfoRepository;
	@Autowired
	FundManagerInfoRepository fundManagerInfoRepository;
	@Autowired
	FundMessageRepository fundMessageRepository;

	@RequestMapping(value = "/anyone/archive/detail", method = RequestMethod.GET)
	public Message getFundArchiveDetail(@RequestParam("code") String code) {
		FundArchiveDetail ret = fundBasicInfoRepository.getFundArchiveDetailByFundCode(code);
		List<String> managerList = fundManagerInfoRepository.getManagerDescriptionByFundCode(code);
		List<FundDescription> descriptionList = fundMessageRepository.getFundDescriptionByFundCode(code);
		ret.setManagerList(managerList);
		ret.setDescriptionList(descriptionList);
		return new Message(MessageUtil.SUCCESS, (JSONObject) JSON.toJSON(ret));
	}
}
