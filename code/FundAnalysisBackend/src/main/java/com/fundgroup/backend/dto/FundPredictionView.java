package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FundPredictionView {

  @JSONField(name = "lastUpdateDate")
  LocalDateTime lastUpdateDate;

  @JSONField(name = "hasModel")
  boolean hasModel;

  @JSONField(name = "oneDayNAV")
  Double oneDayNAV;

  @JSONField(name = "twoDayNAV")
  Double twoDayNAV;

  @JSONField(name = "threeDayNAV")
  Double threeDayNAV;

  @JSONField(name = "oneDayQuote")
  Double oneDayQuote;

  @JSONField(name = "twoDayQuote")
  Double twoDayQuote;

  @JSONField(name = "threeDayQuote")
  Double threeDayQuote;

  @JSONField(name = "text")
  String text;

}
