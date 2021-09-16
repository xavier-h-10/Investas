package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class ActiveCompetitionUser {
	@JSONField(name = "competitionId")
	private Integer competitionId;
	@JSONField(name = "competitionName")
	private String competitionName;
	@JSONField(name = "endDate")
	private LocalDate endDate;
	@JSONField(name = "capacity")
	private BigInteger capacity;
	@JSONField(name = "number")
	private BigInteger number;
	@JSONField(name = "surplusMoney")
	private BigDecimal surplusMoney;
	@JSONField(name = "creatorId")
	private Long creatorId;
	@JSONField(name = "competitionDescription")
	private String competitionDescription;

	public ActiveCompetitionUser(Integer competitionId, String competitionName, LocalDate endDate, BigInteger capacity,
								 BigInteger number, BigDecimal surplusMoney, Long creatorId,
								 String competitionDescription) {
		this.competitionId = competitionId;
		this.competitionName = competitionName;
		this.endDate = endDate;
		this.capacity = capacity;
		this.number = number;
		this.surplusMoney = surplusMoney;
		this.creatorId = creatorId;
		this.competitionDescription = competitionDescription;
	}
}
