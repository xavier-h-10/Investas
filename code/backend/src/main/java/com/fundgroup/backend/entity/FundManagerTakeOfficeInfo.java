package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name="fund_manager_take_office_info")
public class FundManagerTakeOfficeInfo {
    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="info_id")
    private BigInteger infoId;

    @NotNull
    @Column(name="manager_id",length=8)
    private String managerId;

    @NotNull
    @Column(name="fund_code",length=6)
    private String fundCode;

    @NotNull
    @Column(name="start_time")
    private LocalDate startTime;

    @Column(name="end_time")
    @ColumnDefault("9999-12-31")
    private LocalDate endTime;

    @Column(name="repay_rate",precision = 8,scale = 2)
    private Double repayRate;

    @Column(name="fund_manager_take_office_status")
    @ColumnDefault("0")
    private Integer fundManagerTakeOfficeStatus;

}
