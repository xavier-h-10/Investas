package com.fundgroup.backend.service;

import com.fundgroup.backend.dto.FundSearch;

import java.util.List;

public interface SearchService {
	void load(List<FundSearch> data);

	List<FundSearch> search(String search, Integer page, Integer size);
}
