package com.fundgroup.backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FundAssembly {
    private String fundCode;
    private String fundName;
    private Integer fundType;
    private BigDecimal assetSize;
    private LocalDate fundEstablishDate;
    private Integer fundRating;


    private Double NAV;
    private Double accumulativeNAV;
    private LocalDate updateDate;

    private Double lastOneDayRate;
    private Double lastOneWeekRate;
    private Double lastOneMonthRate;
    private Double lastThreeMonthRate;
    private Double lastSixMonthRate;
    private Double lastOneYearRate;
    private Double lastTwoYearsRate;
    private Double lastThreeYearsRate;
    private Double lastFiveYearsRate;
    private Double fromBeginningRate;
    private Double fromThisYearRate;

    private Integer lastOneDayRank;
    private Integer lastOneWeekRank;
    private Integer lastOneMonthRank;
    private Integer lastThreeMonthRank;
    private Integer lastSixMonthRank;
    private Integer lastOneYearRank;
    private Integer lastTwoYearsRank;
    private Integer lastThreeYearsRank;
    private Integer lastFiveYearsRank;
    private Integer fromBeginningRank;
    private Integer fromThisYearRank;

    private FundPredictionView fundPredictionView;

    public FundAssembly(String fundCode)
    {
        this.fundCode=fundCode;
    }

    public FundAssembly(String fundCode, String fundName, Integer fundType,
                        BigDecimal assetSize,LocalDate fundEstablishDate,
                        Double NAV,Double accumulativeNAV,LocalDate updateDate,
                        Double lastOneDayRate,Double lastOneWeekRate,Double lastOneMonthRate,
                        Double lastThreeMonthRate,Double lastSixMonthRate,Double lastOneYearRate,
                        Double fromBeginningRate)
    {
        this.fundCode=fundCode;
        this.fundName=fundName;
        this.lastOneYearRate=lastOneYearRate;
        this.lastOneDayRate=lastOneDayRate;
        this.NAV=NAV;
        this.fundType=fundType;
        this.accumulativeNAV=accumulativeNAV;
        this.lastOneWeekRate=lastOneWeekRate;
        this.lastOneMonthRate=lastOneMonthRate;
        this.lastThreeMonthRate=lastThreeMonthRate;
        this.lastSixMonthRate=lastSixMonthRate;
        this.updateDate=updateDate;
        this.assetSize=assetSize;
        this.fundEstablishDate=fundEstablishDate;
        this.fromBeginningRate = fromBeginningRate;
    }

}
