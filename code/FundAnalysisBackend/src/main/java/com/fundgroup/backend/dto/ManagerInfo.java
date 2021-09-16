package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ManagerInfo {
	@JSONField(name = "id")
	String id;
	@JSONField(name = "name")
	String name;
	@JSONField(name = "start")
	LocalDate start;
	@JSONField(name = "description")
	String description;
	@JSONField(name = "url")
	String url;
	@JSONField(name = "fundList")
	List<ManagerInfoFund> fundList;

	public ManagerInfo(String id, String name, LocalDate start, String description, String url) {
		super();
		this.id = id;
		this.name = name;
		this.start = start;
		this.description = description;
		this.url = url;
		this.fundList = new ArrayList<ManagerInfoFund>();
	}
}
