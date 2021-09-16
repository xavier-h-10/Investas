package com.fundgroup.backend.entity;

import com.alibaba.fastjson.JSONObject;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@Entity
@Table(name = "fund_prediction")
public class FundPrediction {
	@Id
	@NotNull
	@Column(name = "fund_code", length = 6)
	private String fundCode;

	@Column(name = "fund_type")
	private Integer fundType;

	@NotNull
	@Column(name = "last_update_timestamp", columnDefinition = "TIMESTAMP")
	private LocalDateTime lastUpdateTimestamp;

	@Column(name = "future_one_day_NAV", precision = 7, scale = 4)
	@ColumnDefault("0")
	private Double futureOneDayNAV;

	@Column(name = "one_day_quote_change", precision = 7, scale = 2)
	@ColumnDefault("-1")
	private Double futureOneDayChange;

	@Column(name = "future_two_day_NAV", precision = 7, scale = 4)
	@ColumnDefault("0")
	private Double futureTwoDaysNAV;

	@Column(name = "two_day_quote_change", precision = 7, scale = 2)
	@ColumnDefault("-1")
	private Double futureTwoDaysChange;

	@Column(name = "future_three_day_NAV", precision = 7, scale = 4)
	@ColumnDefault("0")
	private Double futureThreeDaysNAV;

	@Column(name = "three_day_quote_change", precision = 7, scale = 2)
	@ColumnDefault("-1")
	private Double futureThreeDaysChange;

	@Column(name = "description_id")
	private Integer descriptionId;

	public FundPrediction(String fundCode) {
		this.fundCode = fundCode;
	}

	public FundPrediction(String fundCode, Integer fundType,
			LocalDateTime lastUpdateTimestamp, Double futureOneDayNAV, Double futureOneDayChange,
			Double futureTwoDaysNAV, Double futureTwoDaysChange, Double futureThreeDaysNAV,
			Double futureThreeDaysChange) {
		this.fundCode = fundCode;
		this.fundType = fundType;
		this.lastUpdateTimestamp = lastUpdateTimestamp;
		this.futureOneDayNAV = futureOneDayNAV;
		this.futureOneDayChange = futureOneDayChange;
		this.futureTwoDaysNAV = futureTwoDaysNAV;
		this.futureTwoDaysChange = futureTwoDaysChange;
		this.futureThreeDaysNAV = futureThreeDaysNAV;
		this.futureThreeDaysChange = futureThreeDaysChange;
	}

	public FundPrediction() {
	}


}
