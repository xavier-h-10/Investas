package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class PositionInfoFund {
	@JSONField(name = "code")
	String code;
	@JSONField(name = "name")
	String name;
	@JSONField(name = "nav")
	Double nav;
	@JSONField(name = "navChange")
	Double navChange;
	@JSONField(name = "estimateNav")
	Double estimateNav;
	@JSONField(name = "estimateNavChange")
	Double estimateNavChange;
	@JSONField(name = "estimateTime")
	LocalDateTime estimateTime;

	public PositionInfoFund(String code) {
		this.code = code;
	}

	public void setEstimate(FundEstimateSimp fundEstimateSimp) {
		this.estimateNav = fundEstimateSimp.getNAVEstimate();
		this.estimateNavChange = fundEstimateSimp.getIncreaseEstimate();
		this.estimateTime = fundEstimateSimp.getEstimateTimestamp();
	}
}
