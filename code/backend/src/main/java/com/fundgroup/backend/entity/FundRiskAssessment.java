package com.fundgroup.backend.entity;

import com.alibaba.fastjson.JSONObject;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Table(name="fund_risk_assessment")
public class FundRiskAssessment {
  @Id
  @NotNull
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id")
  private Integer id;

  @NotNull
  @Column(name="order",unique = true)
  private Integer order;

  @NotNull
  @Column(name="question")
  private String question;

  @Column(name="option_1")
  private String option_1;

  @Column(name="option_2")
  private String option_2;

  @Column(name="option_3")
  private String option_3;

  @Column(name="option_4")
  private String option_4;

  @Column(name="option_5")
  private String option_5;

  @Column(name="score_1")
  private Integer score_1;

  @Column(name="score_2")
  private Integer score_2;

  @Column(name="score_3")
  private Integer score_3;

  @Column(name="score_4")
  private Integer score_4;

  @Column(name="score_5")
  private Integer score_5;

  @CreationTimestamp
  @Column(name = "created_at")
  private Timestamp createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Timestamp updatedAt;
}
