package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class FundStockSimp {
    @JSONField(name = "stockId")
    String stockId;
    @JSONField(name = "stockName")
    String stockName;
    @JSONField(name = "price")
    Double price;
    @JSONField(name = "changePercentage")
    Double changePercentage;

    public FundStockSimp(){}

    public FundStockSimp(String stockId,String stockName,Double price,Double changePercentage)
    {
        this.stockId=stockId;
        this.stockName=stockName;
        this.price=price;
        this.changePercentage=changePercentage;
    }
}
