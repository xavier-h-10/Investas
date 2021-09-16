package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.FundPredictionErrorDaoImpl;
import com.fundgroup.backend.repository.FundPredictionErrorRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class FundPredictionErrorDaoTest {
	@Mock
	FundPredictionErrorRepository fundPredictionErrorRepository;

	@InjectMocks
	FundPredictionErrorDaoImpl fundPredictionErrorDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundPredictionErrorRepository.getErrorMonitorData()).thenReturn(null);
		Mockito.when(fundPredictionErrorRepository.getErrorMax()).thenReturn(null);
		Mockito.when(fundPredictionErrorRepository.getErrorMin()).thenReturn(null);
		Mockito.when(fundPredictionErrorRepository.getErrorByCode("000689")).thenReturn(null);
	}

	@Test
	public void testGetErrorMonitorData() {
		Object ret = fundPredictionErrorDao.getErrorMonitorData();
		Assert.assertNull(ret);
		Mockito.verify(fundPredictionErrorRepository).getErrorMonitorData();
	}

	@Test
	public void testGetErrorMax() {
		Object ret = fundPredictionErrorDao.getErrorMax();
		Assert.assertNull(ret);
		Mockito.verify(fundPredictionErrorRepository).getErrorMax();
	}

	@Test
	public void testGetErrorMin() {
		Object ret = fundPredictionErrorDao.getErrorMin();
		Assert.assertNull(ret);
		Mockito.verify(fundPredictionErrorRepository).getErrorMin();
	}

	@Test
	public void testGetErrorByCode() {
		Object ret = fundPredictionErrorDao.getErrorByCode("000689");
		Assert.assertNull(ret);
		Mockito.verify(fundPredictionErrorRepository).getErrorByCode("000689");
	}
}
