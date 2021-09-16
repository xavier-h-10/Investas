package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class FundArchiveDetail {
	@JSONField(name = "name")
	String name;
	@JSONField(name = "code")
	String code;
	@JSONField(name = "start")
	LocalDate start;
	@JSONField(name = "size")
	BigDecimal size;
	@JSONField(name = "company")
	String company;
	@JSONField(name = "custodian")
	String custodian;
	@JSONField(name = "managerList")
	List<String> managerList;
	@JSONField(name = "descriptionList")
	List<FundDescription> descriptionList;

	public FundArchiveDetail(String name, String code, LocalDate start, BigDecimal size, String company,
							 String custodian) {
		this.name = name;
		this.code = code;
		this.start = start;
		this.size = size;
		this.company = company;
		this.custodian = custodian;
		this.managerList = new ArrayList<>();
		this.descriptionList = new ArrayList<>();
	}
}
