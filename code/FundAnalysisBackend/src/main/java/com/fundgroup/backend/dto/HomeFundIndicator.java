package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class HomeFundIndicator {
	@JSONField(name = "code")
	private String code;
	@JSONField(name = "name")
	private String name;
	@JSONField(name = "yearRate")
	private Double yearRate;
	@JSONField(name = "maxRet")
	private Double maxRet;

	public HomeFundIndicator(String code, String name, Double yearRate, Double maxRet) {
		this.code = code;
		this.name = name;
		this.yearRate = yearRate;
		this.maxRet = maxRet;
	}
}
