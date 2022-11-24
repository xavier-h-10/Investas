package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.cache.FundAssemblyCache;
import com.fundgroup.backend.dto.FundCard;
import com.fundgroup.backend.dto.FundSearch;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.service.FundBasicInfoService;
import com.fundgroup.backend.service.SearchService;
import com.fundgroup.backend.serviceImpl.SearchServiceImpl;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FundInfoController {

    @Autowired
    private FundBasicInfoService fundBasicInfoService;

    @Autowired
    private FundAssemblyCache fundAssemblyCache;


    @RequestMapping(value = "/anyone/search/fund/simplified",method = RequestMethod.GET)
    public Message searchFundSimpInfo(@RequestParam("searchStr") String searchStr, @RequestParam("pageIdx") Integer pageIdx)
    {
        if(pageIdx<0)
            return new Message(MessageUtil.INVALID_ARGUMENT);
        Integer pageSize=10;
//        List<FundBasicInfo> fundBasicInfos=fundBasicInfoService.searchFundByCodeOrName(searchStr,pageIdx,pageSize);
//        JSONArray fundSimpArray=new JSONArray();
//        for(FundBasicInfo fundBasicInfo : fundBasicInfos)
//        {
//            JSONObject info=new JSONObject();
//            info.put("fundCode",fundBasicInfo.getFundCode());
//            info.put("fundName",fundBasicInfo.getFundName());
//            fundSimpArray.add(info);
//        }
        List<FundSearch> fundBasicInfos = fundAssemblyCache.search(searchStr, pageIdx, pageSize);
        JSONObject fundSimp = new JSONObject();
        fundSimp.put("fundSimpArray", fundBasicInfos);
        return new Message(MessageUtil.SUCCESS, fundSimp);
    }

    @RequestMapping(value = "/anyone/fund/card",method = RequestMethod.GET)
    public Message getFundCard(@RequestParam("fundCode") String fundCode)
    {
        FundCard fundCard=fundBasicInfoService.searchFundCardByCode(fundCode);
        if(fundCard==null)
            return new Message(MessageUtil.ERROR);
        JSONObject jsonObject= (JSONObject) JSON.toJSON(fundCard);
        return new Message(MessageUtil.SUCCESS,jsonObject);

    }

}
