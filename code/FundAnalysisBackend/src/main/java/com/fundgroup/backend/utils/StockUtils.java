package com.fundgroup.backend.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class StockUtils {
    RestTemplate restTemplate=new RestTemplate();
    String url="https://push2.eastmoney.com/api/qt/stock/details/get?fields1=f1,f2,f3,f4&fields2=f51,f52,f53,f54,f55&secid=%s.%s";
    String trend_url="https://push2.eastmoney.com/api/qt/stock/trends2/get?fields1=f1,f2,f3,f4,f5&fields2=f51&secid=0.000858";
    String url2="https://appsuggest.1234567.com.cn/FundMSearchApi/FundMSearch40_SharesMore?product=EFund&appVersion=6.4.6&ServerVersion=6.4.6&Code=";
    String url2Back="&CustomerNo=&version=6.4.6&deviceid=41ecc8babe2fb37ca075b6a863a59ddb%7C%7Ciemi_tluafed_me&DCTEXCH=1&MobileKey=41ecc8babe2fb37ca075b6a863a59ddb\\%7C\\%7Ciemi_tluafed_me&UserId=&OSVersion=10&plat=Android&UToken=&CToken=&FundbarChannal=Appstore";

    private Integer codeToInteger(String stockId)
    {
        Integer length=stockId.length();
        //美股
        //TODO:根据上市证交所不同有105、106
        if(stockId.endsWith(".US"))
        {
            return 105;
        }
        //港股
        if(length==5)
        {
            return 116;
        }

        if(length==6)
        {
            //沪市AB
            if (stockId.charAt(0)=='6'||stockId.charAt(0)=='9')
                return 1;
                //深市AB 创业板 中小板
            else if(stockId.charAt(0)=='0'||stockId.charAt(0)=='2'||stockId.charAt(0)=='3'||
                    stockId.substring(0, Math.min(stockId.length(), 3)).equals("002"))
                return 0;
        }
        return -1;
    }

    private String stockIdHandler(String stockId)
    {
        Integer length=stockId.length();
        //美股
        if(stockId.endsWith(".US"))
        {
            return stockId.substring(0,length-3);
        }
        return stockId;
    }

    public ResponseEntity<String> getStockInfo(String stockId)
    {
        Integer type=codeToInteger(stockId);
        return restTemplate.getForEntity(String.format(url,Integer.toString(type),stockIdHandler(stockId)),String.class);
    }

}
