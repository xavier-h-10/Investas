package com.fundgroup.backend.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class FundArchive {
	@JSONField(name = "code")
	String code;
	@JSONField(name = "size")
	BigDecimal size;
	@JSONField(name = "start")
	LocalDate start;
	@JSONField(name = "managerList")
	List<FundArchiveManager> managerList;
	@JSONField(name = "stockList")
	JSONArray stockList;

	public FundArchive(String code, BigDecimal size, LocalDate start) {
		this.code = code;
		this.size = size;
		this.start = start;
	}
}
