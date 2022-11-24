package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class FundHistoryNAV {
	@JSONField(name = "oneDay")
	List<FundDailySimp> oneDay;
	@JSONField(name = "oneWeek")
	List<FundDailySimp> oneWeek;
	@JSONField(name = "oneMonth")
	List<FundDailySimp> oneMonth;
	@JSONField(name = "threeMonths")
	List<FundDailySimp> threeMonths;
	@JSONField(name = "sixMonths")
	List<FundDailySimp> sixMonths;
	@JSONField(name = "oneYear")
	List<FundDailySimp> oneYear;
	@JSONField(name = "twoYears")
	List<FundDailySimp> twoYears;
	@JSONField(name = "threeYears")
	List<FundDailySimp> threeYears;
	@JSONField(name = "fiveYears")
	List<FundDailySimp> fiveYears;
	@JSONField(name = "begin")
	List<FundDailySimp> begin;
	@JSONField(name = "thisYear")
	List<FundDailySimp> thisYear;
}
