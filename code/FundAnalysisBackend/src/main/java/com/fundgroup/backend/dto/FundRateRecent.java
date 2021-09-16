package com.fundgroup.backend.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class FundRateRecent {
    String fundCode;
    Double lastOneWeek;
    Double lastOneMonth;
    Double lastThreeMonths;
    Double lastSixMonths;
    Double lastOneYear;

    public FundRateRecent(){}

    public FundRateRecent(String fundCode,Double lastOneWeek, Double lastOneMonth,
                          Double lastThreeMonths,Double lastSixMonths,Double lastOneYear)
    {
        this.fundCode=fundCode;
        this.lastOneWeek=lastOneWeek;
        this.lastOneMonth=lastOneMonth;
        this.lastThreeMonths=lastThreeMonths;
        this.lastSixMonths=lastSixMonths;
        this.lastOneYear=lastOneYear;
    }

    public Double getLastOneWeek() {
        return lastOneWeek;
    }

    public Double getLastOneMonth() {
        return lastOneMonth;
    }

    public Double getLastThreeMonths() {
        return lastThreeMonths;
    }

    public Double getLastSixMonths() {
        return lastSixMonths;
    }

    public Double getLastOneYear() {
        return lastOneYear;
    }

    public String getFundCode() {
        return fundCode;
    }

    public JSONObject ToJSONObject()
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("fundCode",fundCode);
        jsonObject.put("lastOneWeek",lastOneWeek);
        jsonObject.put("lastOneMonth",lastOneMonth);
        jsonObject.put("lastThreeMonths",lastThreeMonths);
        jsonObject.put("lastSixMonths",lastSixMonths);
        jsonObject.put("lastOneYear",lastOneYear);
        return jsonObject;
    }
}
