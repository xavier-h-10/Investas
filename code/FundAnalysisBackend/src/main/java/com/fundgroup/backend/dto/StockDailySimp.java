package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StockDailySimp {
    @JSONField(name = "updateDate")
    LocalDate updateDate;
    @JSONField(name = "stock_price")
    Double stockPrice;

    public StockDailySimp(){}

    public StockDailySimp(LocalDate updateDate,Double stockPrice)
    {
        this.updateDate=updateDate;
        this.stockPrice=stockPrice;
    }
}
