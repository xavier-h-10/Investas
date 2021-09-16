package com.fundgroup.backend.entity;


import com.sun.istack.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@Table(name = "fund_prediction_error")
public class FundPredictionError {

  @Id
  @NotNull
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name="error_id")
  private BigInteger errorId;

  @NotNull
  @Column(name = "fund_code", length = 6)
  private String fundCode;

  @Column(name = "fund_type")
  private Integer fundType;

  @NotNull
  @Column(name = "last_update_timestamp", columnDefinition = "TIMESTAMP")
  private LocalDateTime lastUpdateTimestamp;

  @Column(name = "today_MSE", precision = 12, scale = 5)
  @ColumnDefault("0")
  private Double todayMSE;

  @Column(name = "today_RMSE", precision = 12, scale = 5)
  @ColumnDefault("0")
  private Double todayRMSE;

  @Column(name = "today_MAE", precision = 12, scale = 5)
  @ColumnDefault("0")
  private Double todayMAE;

  @Column(name = "today_abs_delta", precision = 7, scale = 2)
  @ColumnDefault("0")
  private Double todayAbsDelta;

//  @Column(name = "yesterday_MSE", precision = 12, scale = 5)
//  @ColumnDefault("0")
//  private Double yesterdayMSE;
//
//  @Column(name = "yesterday_RMSE", precision = 12, scale = 5)
//  @ColumnDefault("0")
//  private Double yesterdayRMSE;
//
//  @Column(name = "yesterday_MAE", precision = 12, scale = 5)
//  @ColumnDefault("0")
//  private Double yesterdayMAE;
//
//  @Column(name = "yesterday_abs_delta", precision = 7, scale = 2)
//  @ColumnDefault("0")
//  private Double yesterdayAbsDelta;
//
//  @Column(name = "three_days_MSE", precision = 12, scale = 5)
//  @ColumnDefault("0")
//  private Double threeDaysMSE;
//
//  @Column(name = "three_days_RMSE", precision = 12, scale = 5)
//  @ColumnDefault("0")
//  private Double threeDaysRMSE;
//
//  @Column(name = "three_days_MAE", precision = 12, scale = 5)
//  @ColumnDefault("0")
//  private Double threeDaysMAE;
//
//  @Column(name = "three_days_abs_delta", precision = 7, scale = 2)
//  @ColumnDefault("0")
//  private Double threeDaysAbsDelta;


  public FundPredictionError(BigInteger errorId, String fundCode, Integer fundType,
      LocalDateTime lastUpdateTimestamp, Double todayMSE, Double todayRMSE, Double todayMAE,
      Double todayAbsDelta) {
    this.errorId = errorId;
    this.fundCode = fundCode;
    this.fundType = fundType;
    this.lastUpdateTimestamp = lastUpdateTimestamp;
    this.todayMSE = todayMSE;
    this.todayRMSE = todayRMSE;
    this.todayMAE = todayMAE;
    this.todayAbsDelta = todayAbsDelta;
  }

  public FundPredictionError() {
  }

  public FundPredictionError(String fundCode, Integer fundType,
      LocalDateTime lastUpdateTimestamp) {
    this.fundCode = fundCode;
    this.fundType = fundType;
    this.lastUpdateTimestamp = lastUpdateTimestamp;
  }
}
