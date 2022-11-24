package com.fundgroup.backend.dto;

import com.fundgroup.backend.entity.FundPrediction;
import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
public class FundPredictionDisplay {

  private String fundCode;

  private LocalDateTime lastUpdateTimestamp;

  private Double oneDayNav;

  private Double twoDayNav;

  private Double threeDayNav;

  private Double oneDayQuote;

  private Double twoDayQuote;

  private Double threeDayQuote;

  private Integer fundType;

  private String FundName;

  private Double originNAV;

  private Double originQuote;

  public FundPredictionDisplay(FundPrediction fundPrediction) {
    this.fundCode = fundPrediction.getFundCode();
    this.lastUpdateTimestamp = fundPrediction.getLastUpdateTimestamp();
    this.oneDayNav = fundPrediction.getFutureOneDayNAV();
    this.twoDayNav = fundPrediction.getFutureTwoDaysNAV();
    this.threeDayNav = fundPrediction.getFutureThreeDaysNAV();
    this.oneDayQuote = fundPrediction.getFutureOneDayChange();
    this.twoDayQuote = fundPrediction.getFutureTwoDaysChange();
    this.threeDayQuote = fundPrediction.getFutureThreeDaysNAV();
    this.fundType = fundPrediction.getFundType();
  }
}
