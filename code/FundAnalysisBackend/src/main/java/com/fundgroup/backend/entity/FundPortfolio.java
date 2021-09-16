package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

@Entity
@Table(name="fund_portfolio")
@Data
public class FundPortfolio {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="info_id")
    private BigInteger infoId;

    @NotNull
    @Column(name="announce_date")
    private Date announceDate;

    @NotNull
    @Column(name="fund_code",length=6)
    private String fundCode;

    @NotNull
    @Column(name="stock_id",length = 8)
    private String stockId;

    @NotNull
    @Column(name="stock_name",length = 255)
    private String stockName;

    @NotNull
    @Column(name="stock_mkv",precision = 20,scale = 5)
    private BigDecimal stockMkv;

    @NotNull
    @Column(name="stock_amount",precision = 20,scale = 5)
    private BigDecimal stockAmount;

    @NotNull
    @Column(name="percentage",precision = 7,scale = 2)
    private Double percentage;

    @Column(name="fund_portfolio_status")
    @ColumnDefault("0")
    private Integer fundPortfolioStatus;

}
