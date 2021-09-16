package com.fundgroup.backend.dto;


import lombok.Data;


@Data
public class FundRateRank {
    private String fundCode;
    private Integer lastOneDay;
    private Integer lastOneWeek;
    private Integer lastOneMonth;
    private Integer lastThreeMonths;
    private Integer lastSixMonths;
    private Integer lastOneYear;
    private Integer lastTwoYears;
    private Integer lastThreeYears;
    private Integer lastFiveYears;
    private Integer fromBeginning;
    private Integer fromThisYear;



    public FundRateRank(){}

    public FundRateRank(String fundCode,Integer lastOneDay,Integer lastOneWeek,
                    Integer lastOneMonth,Integer lastThreeMonths,Integer lastSixMonths,
                    Integer lastOneYear,Integer lastTwoYears,Integer lastThreeYears,
                    Integer lastFiveYears,Integer fromBeginning,Integer fromThisYear)
    {
        this.fundCode=fundCode;
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
}
