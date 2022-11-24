package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="fund_company")
public class FundCompany {
    @Id
    @NotNull
    @Column(name="fund_company_code",length=8)
    private String fundCompanyCode;

    @Column(name="fund_company_name_CN",length=160)
    private String fundCompanyNameCN;

    @Column(name="fund_company_name_EN",length=160)
    private String fundCompanyNameEN;

    @Column(name="fund_company_website",length=160)
    private String fundCompanyWebsite;

    @Column(name="fund_company_address",length=160)
    private String fundCompanyAddress;

    @Column(name="fund_company_tel",length=40)
    private String fundCompanyTel;

    @Column(name="fund_company_reg_capital",precision=30, scale=10)
    private BigDecimal fundCompanyRegCapital;

    @Column(name="rate")
    @ColumnDefault("-1")
    private Integer rate;

    @Column(name="employees")
    @ColumnDefault("0")
    private Integer employees;

    @Column(name="fund_company_status")
    @ColumnDefault("0")
    private Integer fundCompanyStatus;

}
