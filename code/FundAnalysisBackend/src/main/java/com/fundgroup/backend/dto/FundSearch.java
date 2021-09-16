package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class FundSearch {
	@JSONField(name = "fundCode")
	private String code;
	@JSONField(name = "fundName")
	private String name;

	public FundSearch(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
