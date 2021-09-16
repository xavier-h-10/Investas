package com.fundgroup.backend.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FundCard {
    @JSONField(name = "fundCode")
    private String fundCode;
    @JSONField(name = "fundName")
    private String fundName;
    @JSONField(name = "fundType")
    private Integer fundType;
    @JSONField(name = "NAV")
    private Double NAV;
    @JSONField(name = "accumulateNAV")
    private Double accumulateNAV;
    @JSONField(name = "fundRating")
    private Integer fundRating;
    @JSONField(name = "updateDate")
    private LocalDate updateDate;
    @JSONField(name = "lastOneDayRate")
    private Double lastOneDayRate;
    @JSONField(name = "lastOneYearRate")
    private Double lastOneYearRate;
    @JSONField(name = "fromBeginningRate")
    private Double fromBeginningRate;


    public FundCard() { }

    public FundCard(String fundCode, String fundName, Integer fundType, Double NAV, Double accumulateNAV,
                    Integer fundRating, LocalDate updateDate, Double lastOneDayRate, Double lastOneYearRate,
                    Double fromBeginningRate)
    {
        this.fundCode=fundCode;
        this.fundName=fundName;
        this.fundType=fundType;
        this.NAV=NAV;
        this.accumulateNAV=accumulateNAV;
        this.fundRating = fundRating;
        this.updateDate=updateDate;
        this.lastOneDayRate=lastOneDayRate;
        this.lastOneYearRate=lastOneYearRate;
        this.fromBeginningRate = fromBeginningRate;
    }

    public JSONObject toJSONObject()
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("fundCode",this.fundCode);
        jsonObject.put("fundName",this.fundName);
        jsonObject.put("fundType",this.fundType);
        jsonObject.put("NAV",this.NAV);
        jsonObject.put("accumulateNAV",this.accumulateNAV);
        jsonObject.put("fundRating",this.fundRating);
        jsonObject.put("lastOneDayRate",this.lastOneDayRate);
        jsonObject.put("lastOneYearRate",this.lastOneYearRate);
        jsonObject.put("fromBeginningRate", this.fromBeginningRate);
        return jsonObject;
    }

}
