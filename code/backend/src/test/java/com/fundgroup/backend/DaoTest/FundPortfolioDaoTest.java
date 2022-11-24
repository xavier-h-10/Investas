package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.FundPortfolioDaoImpl;
import com.fundgroup.backend.repository.FundPortfolioRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class FundPortfolioDaoTest {
	@Mock
	private FundPortfolioRepository fundPortfolioRepository;

	@InjectMocks
	FundPortfolioDaoImpl fundPortfolioDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundPortfolioRepository.getFundPortfolioByCode("000689")).thenReturn(null);
	}

	@Test
	public void testGetFundCompeUsersByCompetitionId() {
		Object ret = fundPortfolioDao.getFundPortfolioByCode("000689");
		Assert.assertNull(ret);
		Mockito.verify(fundPortfolioRepository).getFundPortfolioByCode("000689");
	}
}
