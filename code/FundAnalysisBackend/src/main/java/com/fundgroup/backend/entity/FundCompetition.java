package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@Entity
@Table(name = "fund_competition")
public class FundCompetition {
  @Id
  @NotNull
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name="competition_id")
  Integer competitionId;

  @NotNull
  @Column(name="competition_name")
  String competitionName;

  @NotNull
  @Column(name="start_date")
  LocalDate startDate;

  @NotNull
  @Column(name="end_date")
  LocalDate endDate;

  @NotNull
  @Column(name = "creator_id")
  Long creatorId;

  @NotNull
  @Column(name="competition_description")
  String competitionDescription;

  //  最大可容纳人数
  @NotNull
  @Column(name="capacity")
  BigInteger capacity;

  //  最大可容纳人数
  @NotNull
  @Column(name="number")
  BigInteger number;

//  起始资金
  @NotNull
  @Column(name="initial_capital")
  BigInteger initialCapital;

  //  允许投资的基金类型。二进制，最右边的第一位代表0型，右边第14位代表第14型
  @NotNull
  @Column(name="allowed_fund_type")
  Integer allowedFundType;

  @NotNull
  @Column(name = "is_public")
  Boolean isPublic;

  public FundCompetition(Integer competitionId, String competitionName, LocalDate startDate,
      LocalDate endDate, Long creatorId, String competitionDescription, BigInteger capacity,
      BigInteger initialCapital, Integer allowedFundType, Boolean isPublic) {
    this.competitionId = competitionId;
    this.competitionName = competitionName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.creatorId = creatorId;
    this.competitionDescription = competitionDescription;
    this.capacity = capacity;
    this.number = BigInteger.valueOf(0);
    this.initialCapital = initialCapital;
    this.allowedFundType = allowedFundType;
    this.isPublic = isPublic;
  }

  public FundCompetition(Integer competitionId) {
    this.competitionId=competitionId;
  }

  public FundCompetition() {
  }

  public FundCompetition(Long creatorId, String competitionName, LocalDate startDate, LocalDate endDate,
      String competitionDescription, BigInteger capacity, BigInteger initialCapital,
      Integer allowedFundType, Boolean isPublic) {
    this.creatorId = creatorId;
    this.competitionName = competitionName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.competitionDescription = competitionDescription;
    this.capacity = capacity;
    this.number = BigInteger.valueOf(0);
    this.initialCapital = initialCapital;
    this.allowedFundType = allowedFundType;
    this.isPublic = isPublic;
  }

  public static Integer vector2Bigint(Vector<Integer> allowed){
    int tar = 0;
    for(Integer type: allowed){
      tar = tar | 1 << type ;
    }
    return tar;
  }

  public static Vector<Integer> Bigint2vector(Integer allowed){
    Vector<Integer> ret = new Vector<>();
    for(int i = 0; i < 12; ++i){
      if((allowed & 1 << i) != 0){
        ret.add(i);
      }
    }

    return ret;
  }

  public Boolean allow(Integer fundType) {
    return (this.allowedFundType >> fundType & 1) == 1;
  }

  @Override
  public String toString() {
    return "FundCompetition{" +
        "competitionId=" + competitionId +
        ", competitionName='" + competitionName + '\'' +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", competitionDescription='" + competitionDescription + '\'' +
        ", capacity=" + capacity +
        ", initialCapital=" + initialCapital +
        ", allowedFundType=" + allowedFundType +
        ", isPublic=" + isPublic +
        '}';
  }
}
