package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import java.sql.Timestamp;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "fund_indicator")
@Data
public class FundIndicator {

  @NotNull
  @Column(name = "fund_code", length = 6)
  @ColumnDefault("000000")
  @Id
  private String fundCode;

  @Column(name = "fund_type")
  @NotNull
  private Integer fundType;

  @Column(name = "SHARP1", precision = 10, scale = 4)
  private Double SHARP1;

  @Column(name = "SHARP3", precision = 10, scale = 4)
  private Double SHARP3;

  @Column(name = "SHARP5", precision = 10, scale = 4)
  private Double SHARP5;

  @Column(name = "SHARP_1NRANK")
  private Integer SHARP_1NRANK;

  @Column(name = "SHARP_3NRANK")
  private Integer SHARP_3NRANK;

  @Column(name = "SHARP_5NRANK")
  private Integer SHARP_5NRANK;

  @Column(name = "MAXRETRA1", precision = 10, scale = 4)
  private Double MAXRETRA1;

  @Column(name = "MAXRETRA3", precision = 10, scale = 4)
  private Double MAXRETRA3;

  @Column(name = "MAXRETRA5", precision = 10, scale = 4)
  private Double MAXRETRA5;

  @Column(name = "MAXRETRA_1NRANK")
  private Integer MAXRETRA_1NRANK;

  @Column(name = "MAXRETRA_3NRANK")
  private Integer MAXRETRA_3NRANK;

  @Column(name = "MAXRETRA_5NRANK")
  private Integer MAXRETRA_5NRANK;

  @Column(name = "STDDEV1", precision = 10, scale = 4)
  private Double STDDEV1;

  @Column(name = "STDDEV3", precision = 10, scale = 4)
  private Double STDDEV3;

  @Column(name = "STDDEV5", precision = 10, scale = 4)
  private Double STDDEV5;

  @Column(name = "STDDEV_1NRANK")
  private Integer STDDEV_1NRANK;

  @Column(name = "STDDEV_3NRANK")
  private Integer STDDEV_3NRANK;

  @Column(name = "STDDEV_5NRANK")
  private Integer STDDEV_5NRANK;

  @Column(name = "PROFIT_Y", precision = 10, scale = 4)
  private Double PROFIT_Y;

  @Column(name = "PROFIT_1N", precision = 10, scale = 4)
  private Double PROFIT_1N;

  @Column(name = "PROFIT_YRANK")
  private Integer PROFIT_YRANK;

  @Column(name = "PROFIT_1NRANK")
  private Integer PROFIT_1NRANK;

  //此处删除外键，否则不方便使用jpa的save方法  20210828
//  @ManyToOne(fetch = FetchType.EAGER)
//  @JoinColumn(name = "fund_type")
//  private FundIndicatorAverage fundIndicatorAverage;

  @CreationTimestamp
  @Column(name = "created_at")
  private Timestamp createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Timestamp updatedAt;
}
