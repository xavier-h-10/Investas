package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

@Entity
@Data
@Table(name="user_position")
public class UserPosition {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="position_id")
    private BigInteger positionId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name="fund_code",length=6)
    private String fundCode;

    @NotNull
    @Column(name="holding_start_date")
    private LocalDate holdingStartDate;

    @NotNull
    @Column(name="start_price",precision = 7,scale = 4)
    private Double startPrice;

    @Column(name="holding_end_date")
    @ColumnDefault("9999-12-31")
    private LocalDate holdingEndDate;

    @Column(name="end_price",precision = 7,scale = 4)
    @ColumnDefault("999.9999")
    private Double endPrice;

    @Column(name="amount",precision = 20,scale = 5)
    @ColumnDefault("0")
    private BigDecimal amount;

}
