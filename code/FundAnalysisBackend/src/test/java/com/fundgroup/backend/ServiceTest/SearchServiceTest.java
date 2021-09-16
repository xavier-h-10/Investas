package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dto.FundSearch;
import com.fundgroup.backend.service.SearchService;
import com.fundgroup.backend.serviceImpl.SearchServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SearchServiceTest {

	SearchService searchService = new SearchServiceImpl();

	@Test
	public void test() {
		List<FundSearch> fundSearches = new ArrayList<>();
		fundSearches.add(new FundSearch("111111", "逆天逆天逆"));
		fundSearches.add(new FundSearch("226892", "天逆"));
		fundSearches.add(new FundSearch("000689", "软件工程"));
		fundSearches.add(new FundSearch("000013", "易方达天天理财货币市场基金"));
		fundSearches.add(new FundSearch("000030", "长城核心优选灵活配置混合型证券投资基金"));
		searchService.load(fundSearches);
		System.out.println(searchService.search("689", 0, 10));
		System.out.println(searchService.search("3", 0, 10));
		System.out.println(searchService.search("天", 0, 10));
	}
}
