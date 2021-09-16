package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.FundRateDaoImpl;
import com.fundgroup.backend.entity.FundRate;
import com.fundgroup.backend.repository.FundRateRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;

public class FundRateDaoTest {
	@Mock
	private FundRateRepository fundRateRepository;

	@InjectMocks
	FundRateDaoImpl fundRateDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundRateRepository.getRateInOneYear("000689")).thenReturn(null);
		Mockito.when(fundRateRepository.getFundRateByFundCode("000689")).thenReturn(null);
		Mockito.when(fundRateRepository.getTopFundRateByMonth()).thenReturn(null);
		Mockito.when(fundRateRepository.getTopFundRateByYear()).thenReturn(null);
		Mockito.when(fundRateRepository.getDailyInfoCountByUpdateDate(LocalDate.now())).thenReturn(null);
	}

	@Test
	public void testUpdateFundRate() {
		fundRateDao.updateFundRate(new ArrayList<>());
	}

	@Test
	public void _testUpdateFundRate() {
		fundRateDao.updateFundRate(new FundRate());
	}

	@Test
	public void testGetFundRecRate() {
		Object ret = fundRateDao.getFundRecRate("000689");
		Assert.assertNull(ret);
		Mockito.verify(fundRateRepository).getRateInOneYear("000689");
	}

	@Test
	public void testGetFundRateByCode() {
		Object ret = fundRateDao.getFundRateByCode("000689");
		Assert.assertNull(ret);
		Mockito.verify(fundRateRepository).getFundRateByFundCode("000689");
	}

	@Test
	public void testGetTopFundRateByMonth() {
		Object ret = fundRateDao.getTopFundRateByMonth();
		Assert.assertNull(ret);
		Mockito.verify(fundRateRepository).getTopFundRateByMonth();
	}

	@Test
	public void testGetTopFundRateByYear() {
		Object ret = fundRateDao.getTopFundRateByYear();
		Assert.assertNull(ret);
		Mockito.verify(fundRateRepository).getTopFundRateByYear();
	}

	@Test
	public void testGetDailyInfoCountByUpdateDate() {
		Object ret = fundRateDao.getDailyInfoCountByUpdateDate(LocalDate.now());
		Assert.assertNull(ret);
		Mockito.verify(fundRateRepository).getDailyInfoCountByUpdateDate(LocalDate.now());
	}
}
