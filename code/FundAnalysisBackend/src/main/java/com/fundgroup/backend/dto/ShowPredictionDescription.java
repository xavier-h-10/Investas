package com.fundgroup.backend.dto;

import lombok.Data;

@Data
public class ShowPredictionDescription {

  String name;

  Integer id;

  Long num;

  public ShowPredictionDescription(String name, Integer id, Long num) {
    this.name = name;
    this.id = id;
    this.num = num;
  }

  public ShowPredictionDescription() {
  }

  public ShowPredictionDescription(Integer id, Long num) {
    this.id = id;
    this.num = num;
  }
}
