package com.fundgroup.backend.entity;

import com.alibaba.fastjson.JSONObject;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="fund_yield_rate")
public class FundRate {
    @Id
    @NotNull
    @Column(name="fund_code",length=6)
    private String fundCode;

    @NotNull
    @Column(name="last_update_timestamp", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdateTimestamp;

    @Column(name="last_one_day",precision = 7,scale = 2)
    @ColumnDefault("-1")
    private Double lastOneDay;

    @Column(name="last_one_week",precision = 7,scale = 2)
    @ColumnDefault("-1")
    private Double lastOneWeek;

    @Column(name="last_one_month",precision = 7,scale = 2)
    @ColumnDefault("-1")
    private Double lastOneMonth;

    @Column(name="last_three_months",precision = 7,scale = 2)
    @ColumnDefault("-1")
    private Double lastThreeMonths;

    @Column(name="last_six_months",precision = 7,scale = 2)
    @ColumnDefault("-1")
    private Double lastSixMonths;

    @Column(name="last_one_year",precision = 7,scale = 2)
    @ColumnDefault("-1")
    private Double lastOneYear;

    @Column(name="last_two_years",precision = 7,scale = 2)
    @ColumnDefault("-1")
    private Double lastTwoYears;

    @Column(name="last_three_years",precision = 7,scale = 2)
    @ColumnDefault("-1")
    private Double lastThreeYears;

    @Column(name="last_five_years",precision = 7,scale = 2)
    @ColumnDefault("-1")
    private Double lastFiveYears;

    @Column(name="from_beginning",precision = 7,scale = 2)
    @ColumnDefault("-1")
    private Double fromBeginning;

    @Column(name="from_this_year",precision = 7,scale = 2)
    @ColumnDefault("-1")
    private Double fromThisYear;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "fundRate")
    private FundBasicInfo fundBasicInfo;

    public FundRate(){}

    public FundRate(String fundCode,LocalDateTime lastUpdateTimestamp,Double lastOneDay,Double lastOneWeek,
                    Double lastOneMonth,Double lastThreeMonths,Double lastSixMonths,
                    Double lastOneYear,Double lastTwoYears,Double lastThreeYears,
                    Double lastFiveYears,Double fromBeginning,Double fromThisYear)
    {
        this.fundCode=fundCode;
        this.lastUpdateTimestamp=lastUpdateTimestamp;
        this.lastOneDay=lastOneDay;
        this.lastOneWeek=lastOneWeek;
        this.lastOneMonth=lastOneMonth;
        this.lastThreeMonths=lastThreeMonths;
        this.lastSixMonths=lastSixMonths;
        this.lastOneYear=lastOneYear;
        this.lastTwoYears=lastTwoYears;
        this.lastFiveYears=lastFiveYears;
        this.lastThreeYears=lastThreeYears;
        this.fromBeginning=fromBeginning;
        this.fromThisYear=fromThisYear;
    }

    public String getFundCode() {
        return fundCode;
    }

    public Double getLastOneDay() {
        return lastOneDay;
    }

    public Double getLastOneYear() {
        return lastOneYear;
    }

    public Double getLastSixMonths() {
        return lastSixMonths;
    }

    public Double getLastThreeMonths() {
        return lastThreeMonths;
    }

    public Double getLastOneMonth() {
        return lastOneMonth;
    }

    public Double getLastOneWeek() {
        return lastOneWeek;
    }

    public Double getFromBeginning() {
        return fromBeginning;
    }

    public Double getFromThisYear() {
        return fromThisYear;
    }

    public Double getLastFiveYears() {
        return lastFiveYears;
    }

    public Double getLastThreeYears() {
        return lastThreeYears;
    }

    public Double getLastTwoYears() {
        return lastTwoYears;
    }

    public LocalDateTime getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }



    public JSONObject ToJSONObject()
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("fundCode",fundCode);
        jsonObject.put("lastUpdateTimestamp",lastUpdateTimestamp);
        jsonObject.put("lastOneDay",lastOneDay);
        jsonObject.put("lastOneWeek",lastOneWeek);
        jsonObject.put("lastOneMonth",lastOneMonth);
        jsonObject.put("lastThreeMonths",lastThreeMonths);
        jsonObject.put("lastSixMonths",lastSixMonths);
        jsonObject.put("lastOneYear",lastOneYear);
        jsonObject.put("lastTwoYears",lastTwoYears);
        jsonObject.put("lastThreeYears",lastThreeYears);
        jsonObject.put("lastFiveYears",lastFiveYears);
        jsonObject.put("fromBeginning",fromBeginning);
        jsonObject.put("fromThisYear",fromThisYear);
        return jsonObject;
    }
}
