package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dao.FundEstimateDao;
import com.fundgroup.backend.serviceImpl.FundEstimateServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

public class FundEstimateServiceTest {
	@Mock
	private FundEstimateDao fundEstimateDao;
	@InjectMocks
	FundEstimateServiceImpl fundEstimateService;

	LocalDateTime t930 = LocalDate.now().atTime(9,30);

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
//		Mockito.when(fundEstimateDao.getFundEstimateRange("1", t930, any(LocalDateTime.class)))
//				.thenReturn(null);
	}

	@Test
	public void getTodayEstimateByCode() {
		Object ret = fundEstimateService.getTodayEstimateByCode("1");
	}

	@Test
	public void deleteEstimateRange() {
		fundEstimateService.deleteEstimateRange(TimeType.ONE_DAY_UNTIL);
		fundEstimateService.deleteEstimateRange(TimeType.ONE_WEEK_UNTIL);
		fundEstimateService.deleteEstimateRange(TimeType.ONE_MONTH_UNTIL);
		fundEstimateService.deleteEstimateRange(TimeType.FROM_THIS_YEAR);
	}

}
