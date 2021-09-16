package com.fundgroup.backend.service;

import com.fundgroup.backend.dto.HomeRankFund;

import java.util.List;

public interface HomeRankFundService {
	void clearCache();
	List<HomeRankFund> getHistoryTop(Integer page, Integer size, Integer type);
	List<HomeRankFund> getPredictionTop(Integer page, Integer size, Integer type);
}
