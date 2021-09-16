package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="stock_basic_info")
@Data
public class StockBasicInfo {
    @Id
    @NotNull
    @Column(name="stock_id",length = 8)
    private String stockId;

    @NotNull
    @Column(name="stock_name",length = 255)
    private String stockName;

    @NotNull
    @Column(name="stock_full_name",length = 255)
    private String stockFullName;

    @NotNull
    @Column(name="stock_sort",length = 40)
    private String stockSort;

    @NotNull
    @Column(name="stock_price",precision = 7,scale = 2)
    private Double stockPrice;

    @NotNull
    @Column(name="newest_rate",precision = 5,scale = 2)
    private Double newestRate;

    @Column(name="status")
    @ColumnDefault("0")
    private Integer status;

    public StockBasicInfo(){}

    public StockBasicInfo(String stockId,Double newestRate)
    {
        this.stockId=stockId;
        this.newestRate=newestRate;
    }

}
