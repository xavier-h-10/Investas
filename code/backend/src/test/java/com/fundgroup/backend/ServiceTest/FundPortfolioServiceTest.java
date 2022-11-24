package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dao.FundPortfolioDao;
import com.fundgroup.backend.serviceImpl.FundPortfolioServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FundPortfolioServiceTest {
	@Mock
	private FundPortfolioDao fundPortfolioDao;

	@InjectMocks
	FundPortfolioServiceImpl fundPortfolioService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetErrorMonitorData() {
		fundPortfolioService.getFundPortfolioByCode("1");
	}
}
