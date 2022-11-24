package com.fundgroup.backend.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.service.StockBasicService;
import com.fundgroup.backend.utils.StockUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StockBasicServiceImpl implements StockBasicService {

    private StockUtils stockUtils=new StockUtils();

    @Override
    public JSONObject getStockInfoById(String stockId)
    {
        //try get in Shanghai
        ResponseEntity<String> response = stockUtils.getStockInfo(stockId);
        HttpStatus statusCode = response.getStatusCode();
        if(response.getStatusCode().value()!=200)
            return null;
        String body= response.getBody();
        JSONObject jsonObject= JSON.parseObject(body);
        JSONObject data=jsonObject.getJSONObject("data");
        if(data==null)
            return null;
        String code=(String)data.get("code");
        if(code==null)
            return null;
        double prePrice=((BigDecimal)data.get("prePrice")).doubleValue();
        JSONArray details=data.getJSONArray("details");
        int detailSize=details.size();
        if(detailSize==0)
            return null;
        String curStr=details.getString(detailSize-1);
        String curPriceStr=curStr.split(",")[1];
        curPriceStr=String.format("%.2f",Double.parseDouble(curPriceStr));
        String curTime=curStr.split(",")[0];
        double curPrice=Double.parseDouble(curPriceStr);
        Double change=(curPrice-prePrice)/prePrice*100;
        JSONObject stockJson=new JSONObject();
        stockJson.put("value",String.format("%.2f",change));
        return stockJson;
    }

//    @Override
//    public JSONObject getStockInfoByIdUrl2(String stockId)
//    {
//        ResponseEntity<String> response = stockUtils.getStockInfo(stockId);
//        System.out.println(response.getBody());
//        HttpStatus statusCode = response.getStatusCode();
//        if(response.getStatusCode().value()!=200)
//            return null;
//        String body= response.getBody();
//        JSONObject jsonObject= JSON.parseObject(body);
//        JSONObject data=jsonObject.getJSONObject("Datas");
//        if(data==null)
//            return null;
//        String priceStr=(String)data.get("Close");
//        Double price;
//        if(priceStr==null)
//        {
//            price=-10000.0;
//        }
//        else
//            price=Double.parseDouble(priceStr);
//
//        String changePercentageStr=(String)data.get("ChangePercent");
//        Double changePercentage;
//        if(changePercentageStr==null)
//        {
//            changePercentage=-10000.0;
//        }
//        else
//            changePercentage=Double.parseDouble(changePercentageStr);
//
//        String curTime=(String)data.get("TradeTime");
//        if(curTime==null)
//        {
//            curTime="--";
//        }
//        JSONObject stockJson=new JSONObject();
//
//
//        stockJson.put("price",price);
//        stockJson.put("changePercentage",changePercentage);
//        stockJson.put("time",curTime);
//        return stockJson;
//    }


}
