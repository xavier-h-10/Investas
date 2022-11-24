package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

@Data
public class MonitorTimeNum {
  @JSONField(name = "time")
  LocalDateTime localDate;
  @JSONField(name = "num")
  Long num;

  @JSONField(name = "third")
  String data1;
  @JSONField(name = "forth")
  String data2;

  public MonitorTimeNum(LocalDateTime localDate, Long num, String data1, String data2) {
    this.localDate = localDate;
    this.num = num;
    this.data1 = data1;
    this.data2 = data2;
  }

  public MonitorTimeNum(LocalDateTime localDate, Long num) {
    this.localDate = localDate;
    this.num = num;
  }

  public MonitorTimeNum(LocalDate localDate, Long num) {
    this.localDate = LocalDateTime.of(localDate, LocalTime.MAX);
    this.num = num;
  }

  public MonitorTimeNum(Long num, String data1, String data2) {
    this.num = num;
    this.data1 = data1;
    this.data2 = data2;
  }

  public MonitorTimeNum() {
  }
}
