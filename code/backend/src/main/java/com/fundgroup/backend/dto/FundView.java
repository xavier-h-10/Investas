package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fundgroup.backend.entity.FundRate;
import lombok.Data;

@Data
public class FundView {
	@JSONField(name = "card")
	FundCard card;
	@JSONField(name = "archive")
	FundArchive archive;
	@JSONField(name = "historyPerformance")
	FundRateRecent historyPerformance;
	@JSONField(name = "historyNAV")
	FundHistoryNAV historyNAV;
	@JSONField(name = "fundRateRank")
	FundRateRank fundRateRank;
	@JSONField(name = "fundRateTotalCount")
	FundRateRank fundRateTotalCount;
	@JSONField(name="predictionMock")
	FundPredictionView fundPredictionView;
}