package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class HomeRankFund {
	@JSONField(name = "code")
	String code;
	@JSONField(name = "name")
	String name;
	@JSONField(name = "rate")
	Double rate;

	public HomeRankFund(String code, String name, Double rate) {
		this.code = code;
		this.name = name;
		this.rate = rate;
	}
}
