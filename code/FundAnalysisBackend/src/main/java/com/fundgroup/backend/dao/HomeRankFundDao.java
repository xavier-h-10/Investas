package com.fundgroup.backend.dao;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dto.FundPredictionView;
import com.fundgroup.backend.dto.HomeRankFund;

import java.util.List;

public interface HomeRankFundDao {
	List<HomeRankFund> getHistoryTop(Integer page, Integer size, Integer type);
	List<HomeRankFund> getPredictionTop(Integer page, Integer size, Integer type);

	FundPredictionView getPredictionViewByFundCode(String fundCode);
}
