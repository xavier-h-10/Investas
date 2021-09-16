package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FundDailySimp {
	@JSONField(name = "updateDate")
	LocalDate updateDate;
	@JSONField(name = "NAV")
	Double NAV;
	@JSONField(name = "accumulateNAV")
	Double accumulateNAV;

	public FundDailySimp() {
	}

	public FundDailySimp(LocalDate updateDate, Double NAV) {
		this.updateDate = updateDate;
		this.NAV = NAV;
		this.accumulateNAV = null;
	}

	public FundDailySimp(LocalDate updateDate, Double NAV, double accumulateNAV) {
		this.updateDate = updateDate;
		this.NAV = NAV;
		this.accumulateNAV = accumulateNAV;
	}
}
