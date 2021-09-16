package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import java.math.BigInteger;
import java.util.List;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "prediction_description")
public class PredictionDescription {
  @Id
  @NotNull
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name="description_id")
  private Integer descriptionId;

  @Column(name = "name", length = 40)
  @ColumnDefault("")
  private String name;

  @Column(name = "text", length = 1000)
  @ColumnDefault("")
  private String text;

  @Column(name="priority")
  private Integer priority;

  @OneToMany(targetEntity = PredictionDescriptionRule.class,
      cascade=CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "description_id")
  List<PredictionDescriptionRule> ruleList;

}
