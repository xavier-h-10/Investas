package com.fundgroup.backend.dto;

import lombok.Data;

@Data
public class FundBasicInfoAndRecentRate {

  private String fundCode;
  private String fundName;
  private Integer fundType;

  private Double lastOneMonthRate;
  private Double lastOneYearRate;


}
