package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name="fund_estimate")
@Data
public class FundEstimate {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="estimate_id")
    private BigInteger infoId;

    @NotNull
    @Column(name="fund_code",length=6)
    private String fundCode;

    @NotNull
    @Column(name="estimate_timestamp", columnDefinition = "TIMESTAMP")
    private LocalDateTime estimateTimestamp;

    @Column(name="NAV_estimate",precision = 8,scale = 4)
    @ColumnDefault("9999.9999")
    private Double NAVEstimate;

    @Column(name="increase_estimate",precision = 5,scale = 2)
    @ColumnDefault("999.99")
    private Double increaseEstimate;
}
