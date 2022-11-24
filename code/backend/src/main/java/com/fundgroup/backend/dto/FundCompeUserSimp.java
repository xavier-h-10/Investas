package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fundgroup.backend.entity.FundCompeUser;
import com.fundgroup.backend.entity.FundCompeUserPos;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FundCompeUserSimp {
	@JSONField(name = "id")
	private Long id;

	@JSONField(name = "competitionId")
	private Integer competitionId;

	@JSONField(name = "userId")
	private Long userId;

	@JSONField(name = "surplusMoney")
	private BigDecimal surplusMoney;

	@JSONField(name = "totalAsset")
	private BigDecimal totalAsset;

	@JSONField(name = "totalChange")
	private BigDecimal totalChange;

	@JSONField(name = "isEnd")
	private Boolean isEnd;

	public FundCompeUserSimp(FundCompeUser fundCompeUser) {
		this.competitionId = fundCompeUser.getCompetitionId();
		this.id = fundCompeUser.getId();
		this.isEnd = fundCompeUser.getIsEnd();
		this.surplusMoney = fundCompeUser.getSurplusMoney();
		this.totalAsset = fundCompeUser.getTotalAsset();
		this.totalChange = fundCompeUser.getTotalChange();
		this.userId = fundCompeUser.getUserId();
	}
}
