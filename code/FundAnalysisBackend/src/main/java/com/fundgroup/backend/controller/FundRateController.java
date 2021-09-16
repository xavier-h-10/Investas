package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.constant.Mode;
import com.fundgroup.backend.dto.FundRankingByRecentRate;
import com.fundgroup.backend.service.FundRateService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import java.util.List;

import java.time.LocalDate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FundRateController {
    @Autowired
    private FundRateService fundRateService;

    /**
     * TODO:change auth to admin
     * @return
     */
    @RequestMapping(value="/anyone/updateFundRate",method= RequestMethod.POST)
    public Message updateFundRate(@RequestBody Map<String, Integer> params)
    {
        Integer year=params.get("year");
        Integer month=params.get("month");
        Integer day=params.get("day");
        LocalDate date=LocalDate.of(year,month,day);
        boolean result=fundRateService.updateFundRate(date);
        if(!result)
        {
            return new Message(MessageUtil.ERROR);
        }
        return new Message(MessageUtil.SUCCESS);
    }

    /**
     *
     * @param fundCode
     * @param mode 0:show recent 1:show all
     * @return
     */
    @RequestMapping(value="/anyone/fund/recent-rate",method= RequestMethod.GET)
    public Message getFundRecentRate(@RequestParam("fundCode") String fundCode,@RequestParam("mode") Integer mode)
    {
        if(mode.equals(Mode.SHOW_PART))
        {
            JSONObject fundRecent=fundRateService.getFundRecRate(fundCode);
            return new Message(MessageUtil.SUCCESS,fundRecent);
        }
        else if(mode.equals(Mode.SHOW_ALL))
        {
            JSONObject fundRate=fundRateService.getFundRateByCode(fundCode);
            return new Message(MessageUtil.SUCCESS,fundRate);
        }
        return new Message(MessageUtil.ERROR);
    }

    /**
     *
     * @param params: type: 0, month; 1, year
     * @return message: data is a list fund basic info and recent rate
     */
    @RequestMapping(value="/anyone/fund/recent-rate",method= RequestMethod.POST)
    public Message getTopFundByRanking(@RequestBody Map<String, Integer> params)
    {
        Integer type = params.get("type");
        List<FundRankingByRecentRate> fundRankingByRecentRates = fundRateService.getFundByRateRanking(type);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rank", fundRankingByRecentRates);
        return new Message(MessageUtil.SUCCESS, jsonObject);
    }
}
