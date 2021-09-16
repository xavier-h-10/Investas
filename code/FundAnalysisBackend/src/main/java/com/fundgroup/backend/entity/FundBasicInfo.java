package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "fund_basic_info")
@Data
public class FundBasicInfo {
    @Id
    @NotNull
    @Column(name = "fund_code", length = 6)
    private String fundCode;

    @NotNull
    @Column(name = "fund_name", length = 120)
    private String fundName;

    @NotNull
    @Column(name = "fund_name_abbr", length = 120)
    private String fundNameAbbr;

    @NotNull
    @Column(name = "fund_manager_code", length = 8)
    private String fundManagerCode;

    @NotNull
    @Column(name = "custodian_name", length = 64)
    private String custodianName;

    @Column(name = "currency_type", length = 3)
    @ColumnDefault("CNY")
    private String currencyType;

    @NotNull
    @Column(name = "fund_establish_date", columnDefinition = "DATE")
    private LocalDate fundEstablishDate;

    @NotNull
    @Column(name = "fund_end_date", columnDefinition = "DATE")
    private LocalDate fundEndDate;

    @NotNull
    @Column(name = "fund_type")
    private Integer fundType;

    @Column(name = "fund_status")
    @ColumnDefault("0")
    private Integer fundStatus;

    @Column(name = "fund_purchase_rate", precision = 5, scale = 3)
    @NotNull
    private Double fundPurchaseRate;

    @Column(name = "discounted_fund_purchase_rate", precision = 5, scale = 3)
    @NotNull
    private Double discountedFundPurchaseRate;

    @Column(name = "issue_amount", precision = 20, scale = 6)
    @ColumnDefault("0")
    private BigDecimal issueAmount;

    @Column(name = "manage_fee", precision = 5, scale = 2)
    @ColumnDefault("0")
    private BigDecimal manageFee;

    @Column(name = "asset_size", precision = 20, scale = 6)
    @ColumnDefault("0")
    private BigDecimal assetSize;

    @Column(name = "benchmark", length = 255)
    private String benchmark;

    @Column(name = "has_model")
    private Boolean has_model;

    // @OneToMany(fetch = FetchType.LAZY,mappedBy = "fundBasicInfo",cascade =
    // CascadeType.ALL)
    // private List<FundDailyInfo> fundDailyInfoList;
    @Column(name="fund_rating")
    @ColumnDefault("0")
    private Integer fundRating;


//    @OneToMany(fetch = FetchType.LAZY,mappedBy = "fundBasicInfo",cascade = CascadeType.ALL)
//    private List<FundDailyInfo> fundDailyInfoList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_code")
    private FundRate fundRate;

    public FundBasicInfo() {
    }

    public FundBasicInfo(String fundCode) {
        this.fundCode = fundCode;
    }

    public FundBasicInfo(String fundCode, String fundName, String fundNameAbbr, String fundManagerCode,
            String custodianName, String currencyType, LocalDate fundEstablishDate, LocalDate fundEndDate,
            Integer fundType, Integer fundStatus, Double fundPurchaseRate, Double discountedFundPurchaseRate,
            BigDecimal issueAmount, BigDecimal manageFee, BigDecimal assetSize, String benchmark) {
        this.fundCode = fundCode;
        this.fundName = fundName;
        this.fundNameAbbr = fundNameAbbr;
        this.fundManagerCode = fundManagerCode;
        this.custodianName = custodianName;
        this.currencyType = currencyType;
        this.fundEstablishDate = fundEstablishDate;
        this.fundEndDate = fundEndDate;
        this.fundType = fundType;
        this.fundStatus = fundStatus;
        this.fundPurchaseRate = fundPurchaseRate;
        this.discountedFundPurchaseRate = discountedFundPurchaseRate;
        this.issueAmount = issueAmount;
        this.manageFee = manageFee;
        this.assetSize = assetSize;
        this.benchmark = benchmark;
    }

}
