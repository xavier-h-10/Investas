package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PositionInfo {
	@JSONField(name = "preDate")
	LocalDate preDate;
	@JSONField(name = "curDate")
	LocalDate curDate;
	@JSONField(name = "fundList")
	List<PositionInfoFund> fundList;
}