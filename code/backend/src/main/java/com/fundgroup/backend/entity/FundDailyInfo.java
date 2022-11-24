package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name="fund_daily_info")
@Data
public class FundDailyInfo {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="info_id")
    private BigInteger infoId;

    @NotNull
    @Column(name="fund_code",length=6)
    private String fundCode;

    @Column(name="total_fund_vol",precision = 16,scale = 2)
    @ColumnDefault("0")
    private Double totalFundVol;

    @Column(name="NAV",precision = 7,scale = 4)
    @ColumnDefault("0")
    private Double NAV;

    @Column(name="update_date", columnDefinition = "DATE")
    @ColumnDefault("0")
    private LocalDate updateDate;

    @Column(name="accumulative_NAV",precision = 7,scale = 4)
    @ColumnDefault("0")
    private Double accumulativeNAV;

    @Column(name="accumulative_per_div",precision = 16,scale = 2)
    @ColumnDefault("0")
    private Double accumulativePerDiv;

    @Column(name="fund_size",precision = 16,scale = 2)
    @ColumnDefault("0")
    private Double fundSize;

    @Column(name="fund_status")
    @ColumnDefault("0")
    private Integer fundStatus;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(referencedColumnName = "fund_code")
//    private FundBasicInfo fundBasicInfo;

    public FundDailyInfo(){}

    public FundDailyInfo(String fundCode,Double totalFundVol,Double NAV,LocalDate updateDate,Double accumulativeNAV,
                         Double accumulativePerDiv,Double fundSize,Integer fundStatus)
    {
        this.fundCode=fundCode;
        this.totalFundVol=totalFundVol;
        this.NAV=NAV;
        this.updateDate=updateDate;
        this.accumulativeNAV=accumulativeNAV;
        this.accumulativePerDiv=accumulativePerDiv;
        this.fundSize=fundSize;
        this.fundStatus=fundStatus;
    }

    public Double getAccumulativeNAV()
    {
        return accumulativeNAV;
    }

    public Double getNAV()
    {
        return NAV;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }
}
