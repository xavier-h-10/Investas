package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dto.FundEstimateSimp;
import com.fundgroup.backend.service.FundEstimateService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FundEstimateController {
    @Autowired
    private FundEstimateService fundEstimateService;

    @RequestMapping(value = "/anyone/fund/estimate/today",method = RequestMethod.GET)
    public Message getTodayEstimateByCode(@RequestParam("fundCode") String fundCode)
    {
        List<FundEstimateSimp> fundEstimateSimpList=fundEstimateService.getTodayEstimateByCode(fundCode);
        if(fundEstimateSimpList==null)
            return new Message(MessageUtil.ERROR);

        JSONArray jsonArray=new JSONArray();
        jsonArray.addAll(fundEstimateSimpList);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("estimate",jsonArray);
        System.out.println(fundEstimateSimpList.size());
        return new Message(MessageUtil.SUCCESS,jsonObject);
    }

    /**
     * TODO:modify to admin only
     */
    @RequestMapping(value = "/anyone/fund/estimate/delete",method = RequestMethod.POST)
    public Message deleteRange(@RequestBody Map<String, Object> params)
    {
        Integer timeTypeInt= (Integer) params.get("timeType");
        TimeType timeType=TimeType.values()[timeTypeInt];
        fundEstimateService.deleteEstimateRange(timeType);
        return new Message(MessageUtil.SUCCESS);
    }


}
