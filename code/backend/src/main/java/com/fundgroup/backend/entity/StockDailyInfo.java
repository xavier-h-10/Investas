package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name="stock_daily_info")
@Data
public class StockDailyInfo {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="info_id")
    private BigInteger infoId;

    @NotNull
    @Column(name="stock_id",length = 8)
    private String stockId;

    @Column(name="update_date", columnDefinition = "DATE")
    @ColumnDefault("0")
    private LocalDate updateDate;

    @Column(name="stock_price",precision = 9,scale = 2)
    private Double stockPrice;

    @Column(name="upper_price",precision = 9,scale = 2)
    private Double upperPrice;

    @Column(name="lower_price",precision = 9,scale = 2)
    private Double lowerPrice;

    public StockDailyInfo(){}

    public StockDailyInfo(String stockId,LocalDate updateDate,Double stockPrice,Double upperPrice,Double lowerPrice)
    {
        this.stockId=stockId;
        this.updateDate=updateDate;
        this.stockPrice=stockPrice;
        this.upperPrice=upperPrice;
        this.lowerPrice=lowerPrice;
    }
}
