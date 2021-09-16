package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dao.FundIndicatorDao;
import com.fundgroup.backend.serviceImpl.FundIndicatorServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FundIndicatorServiceTest {
	@Mock
	private FundIndicatorDao fundIndicatorDao;

	@InjectMocks
	FundIndicatorServiceImpl fundIndicatorService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test() {
		fundIndicatorService.fetchFundIndicator();
		fundIndicatorService.getFundIndicatorByCode("1");
		fundIndicatorService.processData();
		fundIndicatorService.wrappedUpdateService().run();
		fundIndicatorService.getFundIndicatorNumber("1");
		fundIndicatorService.getHomeFundIndicator(1, 1, 1, 1, 1, 1);
	}
}
