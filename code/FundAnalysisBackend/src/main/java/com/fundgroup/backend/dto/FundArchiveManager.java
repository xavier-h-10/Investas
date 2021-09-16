package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FundArchiveManager {
	@JSONField(name = "id")
	String id;
	@JSONField(name = "name")
	String name;
	@JSONField(name = "tenure")
	LocalDate tenure;
	@JSONField(name = "start")
	LocalDate start;
	@JSONField(name = "repay")
	Double repay;
	@JSONField(name = "description")
	String description;
	@JSONField(name = "url")
	String url;

	public FundArchiveManager(String id, String name, LocalDate tenure, LocalDate start, Double repay,
							  String description, String url) {
		this.id = id;
		this.name = name;
		this.tenure = tenure;
		this.start = start;
		this.repay = repay;
		this.description = description.split("。")[0] + "。";
		this.url = url;
	}
}
