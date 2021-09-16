package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import java.sql.Timestamp;
import lombok.Data;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "fund_indicator_average")
@Data
public class FundIndicatorAverage {
  @Column(name="fund_type")
  @NotNull
  @Id
  private Integer fundType;

  @Column(name = "SHARP1_AVERAGE", precision = 10, scale = 4)
  private Double SHARP1_AVERAGE;

  @Column(name = "SHARP3_AVERAGE", precision = 10, scale = 4)
  private Double SHARP3_AVERAGE;

  @Column(name = "SHARP5_AVERAGE", precision = 10, scale = 4)
  private Double SHARP5_AVERAGE;

  @Column(name="SHARP_1NFSC")
  private Integer SHARP_1NFSC;

  @Column(name="SHARP_3NFSC")
  private Integer SHARP_3NFSC;

  @Column(name="SHARP_5NFSC")
  private Integer SHARP_5NFSC;

  @Column(name = "MAXRETRA1_AVERAGE", precision = 10, scale = 4)
  private Double MAXRETRA1_AVERAGE;

  @Column(name = "MAXRETRA3_AVERAGE", precision = 10, scale = 4)
  private Double MAXRETRA3_AVERAGE;

  @Column(name = "MAXRETRA5_AVERAGE", precision = 10, scale = 4)
  private Double MAXRETRA5_AVERAGE;

  @Column(name="MAXRETRA_1NFSC")
  private Integer MAXRETRA_1NFSC;

  @Column(name="MAXRETRA_3NFSC")
  private Integer MAXRETRA_3NFSC;

  @Column(name="MAXRETRA_5NFSC")
  private Integer MAXRETRA_5NFSC;

  @Column(name = "STDDEV1_AVERAGE", precision = 10, scale = 4)
  private Double STDDEV1_AVERAGE;

  @Column(name = "STDDEV3_AVERAGE", precision = 10, scale = 4)
  private Double STDDEV3_AVERAGE;

  @Column(name = "STDDEV5_AVERAGE", precision = 10, scale = 4)
  private Double STDDEV5_AVERAGE;

  @Column(name="STDDEV_1NFSC")
  private Integer STDDEV_1NFSC;

  @Column(name="STDDEV_3NFSC")
  private Integer STDDEV_3NFSC;

  @Column(name="STDDEV_5NFSC")
  private Integer STDDEV_5NFSC;

  @Column(name = "PROFIT_Y_AVERAGE", precision = 10, scale = 4)
  private Double PROFIT_Y_AVERAGE;

  @Column(name = "PROFIT_1N_AVERAGE", precision = 10, scale = 4)
  private Double PROFIT_1N_AVERAGE;

  @Column(name="PROFIT_YFSC")
  private Integer PROFIT_YFSC;

  @Column(name="PROFIT_1NFSC")
  private Integer PROFIT_1NFSC;

  @CreationTimestamp
  @Column(name = "created_at")
  private Timestamp createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "type_name")
  private String typeName;

  @Column(name = "type_description")
  private String typeDescription;
}
