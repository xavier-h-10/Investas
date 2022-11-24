package com.fundgroup.backend.dto;

import lombok.Data;

@Data
public class FundCodeType {
  String fundCode;
  Integer fundType;
  Boolean has_model;

  public FundCodeType() {
  }

  public FundCodeType(String fundCode, Integer fundType, Boolean has_model) {
    this.fundCode = fundCode;
    this.fundType = fundType;
    this.has_model = has_model;
  }
}
