package com.fundgroup.backend.dto;

import java.time.temporal.ChronoUnit;
import lombok.Data;

public class RateEntry{
  public ChronoUnit unit;
  public Integer balance;

  public RateEntry(ChronoUnit unit,Integer balance)
  {
    this.unit=unit;
    this.balance=balance;
  }
}
