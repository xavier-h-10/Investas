package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name="fund_manager_info")
public class FundManagerInfo {
    @Id
    @NotNull
    @Column(name="manager_id",length=8)
    private String managerId;

    @NotNull
    @Column(name="fund_company_code",length=8)
    private String fundCompanyCode;

    @NotNull
    @Column(name="manager_name",length=12)
    private String managerName;

    @NotNull
    @Column(name="start_time")
    private LocalDate startTime;

    @Column(name="manager_description",length=4000)
    @ColumnDefault("")
    private String managerDescription;

    @Column(name="manager_url",length=1000)
    @ColumnDefault("")
    private String managerUrl;

    @Column(name="fund_manager_status")
    @ColumnDefault("0")
    private Integer fundManagerStatus;


}
