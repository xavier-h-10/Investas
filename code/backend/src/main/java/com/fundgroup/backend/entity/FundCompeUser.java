package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Data
@Entity
@Table(name = "fund_competition_user")
public class FundCompeUser {
    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotNull
    @Column(name="competition_id")
    private Integer competitionId;

    @NotNull
    @Column(name="user_id")
    private Long userId;

    @NotNull
    @Column(name="surplus_money")
    private BigDecimal surplusMoney;

    @NotNull
    @Column(name="total_asset")
    private BigDecimal totalAsset;

    @NotNull
    @Column(name="total_change")
    private BigDecimal totalChange;

    @NotNull
    @Column(name="is_end")
    private Boolean isEnd;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "fundCompeUser")
    private List<FundCompeUserPos> fundCompeUserPosList;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "fundCompeUser")
    private List<FundCompeUserPosLog> fundCompeUserPosLogList;

    public FundCompeUser(){}

    public FundCompeUser(Integer competitionId,Long userId,BigDecimal surplusMoney,BigDecimal totalAsset,BigDecimal totalChange,Boolean isEnd)
    {
        this.competitionId=competitionId;
        this.userId=userId;
        this.surplusMoney=surplusMoney;
        this.totalAsset=totalAsset;
        this.totalChange=totalChange;
        this.isEnd=isEnd;
    }


}
