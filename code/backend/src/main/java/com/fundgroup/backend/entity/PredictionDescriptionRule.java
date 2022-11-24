package com.fundgroup.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import java.math.BigInteger;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "prediction_description_rule")
public class PredictionDescriptionRule {
  @Id
  @NotNull
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name="rule_id")
  private Integer ruleId;

  @Column(name = "description_id")
  private Integer descriptionId;

  @Column(name = "rule_type")
  private Integer ruleType;

  @Column(name = "rule_orientation")
  private Integer ruleOrientation;

  @Column(name = "rule_value", precision = 10, scale = 5)
  private Double ruleValue;

  @ManyToOne(cascade = CascadeType.ALL)
  @JsonIgnore
  private PredictionDescription predictionDescription;
}
