package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.FundEstimateDaoImpl;
import com.fundgroup.backend.repository.FundEstimateRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FundEstimateDaoTest {
	@Mock
	private FundEstimateRepository fundEstimateRepository;

	@InjectMocks
	FundEstimateDaoImpl fundEstimateDao;

	LocalDateTime localDateTime;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		localDateTime = LocalDateTime.now();
		Mockito.when(fundEstimateRepository.getFundEstimateRange("000689", localDateTime, localDateTime))
				.thenReturn(null);
	}

	@Test
	public void testGetFundCompeUsersByCompetitionId() {
		Object ret = fundEstimateDao.getFundEstimateRange("000689", localDateTime, localDateTime);
		Assert.assertNull(ret);
		Mockito.verify(fundEstimateRepository).getFundEstimateRange("000689", localDateTime, localDateTime);
	}

	@Test
	public void testDeleteRange() {
		fundEstimateDao.deleteRange(localDateTime, localDateTime);
	}
}
