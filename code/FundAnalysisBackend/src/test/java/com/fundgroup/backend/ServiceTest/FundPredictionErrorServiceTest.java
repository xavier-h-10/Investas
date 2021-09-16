package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dao.FundPredictionErrorDao;
import com.fundgroup.backend.service.FundPredictionErrorService;
import com.fundgroup.backend.serviceImpl.FundPredictionErrorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FundPredictionErrorServiceTest {
	@Mock
	FundPredictionErrorDao fundPredictionErrorDao;

	@InjectMocks
	FundPredictionErrorServiceImpl fundPredictionErrorService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test() {
		fundPredictionErrorService.getErrorMonitorData();
		fundPredictionErrorService.getErrorMax();
		fundPredictionErrorService.getErrorMin();
		fundPredictionErrorService.getErrorByCode("1");
	}
}
