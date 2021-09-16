package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.FundRiskAssessmentDaoImpl;
import com.fundgroup.backend.repository.FundRiskAssessmentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FundRiskAssessmentDaoTest {
	@Mock
	private FundRiskAssessmentRepository fundRiskAssessmentRepository;

	@InjectMocks
	FundRiskAssessmentDaoImpl fundRiskAssessmentDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundRiskAssessmentRepository.getAll()).thenReturn(null);
	}

	@Test
	public void testGetAll() {
		Object ret = fundRiskAssessmentDao.getAll();
		Assert.assertNull(ret);
		Mockito.verify(fundRiskAssessmentRepository).getAll();
	}
}
