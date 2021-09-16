package com.fundgroup.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dto.FundStockSimp;
import com.fundgroup.backend.entity.FundPortfolio;
import com.fundgroup.backend.service.FundPortfolioService;
import com.fundgroup.backend.service.StockBasicService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FundPortfolioController {
    @Autowired
    private FundPortfolioService fundPortfolioService;
    @Autowired
    private StockBasicService stockBasicService;

    @RequestMapping(value = "/anyone/fund/portfolio",method = RequestMethod.GET)
    public Message getFundPortfolioByCode(@RequestParam("fundCode") String fundCode)
    {
        List<FundPortfolio> fundPortfolioList=fundPortfolioService.getFundPortfolioByCode(fundCode);
        JSONArray jsonArray=new JSONArray();
        if(fundPortfolioList==null)
            return new Message(MessageUtil.ERROR);
        for(FundPortfolio fundPortfolio:fundPortfolioList)
        {
            JSONObject stockDetail=stockBasicService.getStockInfoById(fundPortfolio.getStockId());
            if(stockDetail==null)
            {
                stockDetail=new JSONObject();
                stockDetail.put("value","");
            }
            stockDetail.put("id",fundPortfolio.getStockId());
            stockDetail.put("name",fundPortfolio.getStockName());
            stockDetail.put("proportion",fundPortfolio.getPercentage());
            System.out.println(stockDetail);

            jsonArray.add(stockDetail);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("fundPortfolioList",jsonArray);
        return new Message(MessageUtil.SUCCESS,jsonObject);
    }
}
