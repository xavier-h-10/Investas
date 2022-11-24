package com.fundgroup.backend.dto;

import com.fundgroup.backend.entity.FundPrediction;
import com.fundgroup.backend.entity.FundRate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FundRankingByRecentRate {

  private String fundCode;

  private String fundName;

  private LocalDateTime lastUpdateTimestamp;

//  private Double lastOneDayNav;

  private Double lastOneDayQuote;

//  private Double lastOneWeekNav;

  private Double lastOneWeekQuote;

//  private Double lastOneMonthNav;

  private Double lastOneMonthQuote;

  private Double lastOneYearQuote;

  private Double fromBeginningQuote;

  public FundRankingByRecentRate(FundRate fundRate) {
    this.setFundCode(fundRate.getFundCode());
    this.setFromBeginningQuote(fundRate.getFromBeginning());
    this.setLastOneMonthQuote(fundRate.getLastOneMonth());
    this.setLastOneDayQuote(fundRate.getLastOneDay());
    this.setLastOneWeekQuote(fundRate.getLastOneWeek());
    this.setLastOneYearQuote(fundRate.getLastOneYear());
    this.setLastUpdateTimestamp(fundRate.getLastUpdateTimestamp());
  }
}
