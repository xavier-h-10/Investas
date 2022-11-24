package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ManagerInfoFund {
	@JSONField(name = "code")
	String code;
	@JSONField(name = "name")
	String name;
	@JSONField(name = "start")
	LocalDate start;
	@JSONField(name = "repay")
	Double repay;

	public ManagerInfoFund(String code, String name, LocalDate start, Double repay) {
		super();
		this.code = code;
		this.name = name;
		this.start = start;
		this.repay = repay;
	}
}
