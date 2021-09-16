package com.fundgroup.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name="fund_model")
public class FundModel {
  @Id
  @NotNull
  @Column(name = "fund_type")
  Integer fundType;

  @NotNull
  @Column(name = "fund_code",length=6)
  String fundCode;

  @Column(name = "running")
  Boolean running;

  public FundModel(Integer fundType) {
    this.fundType = fundType;
  }

  public FundModel(Integer fundType, String fundCode) {
    this.fundType = fundType;
    this.fundCode = fundCode;
  }

  public FundModel() {
  }
}
