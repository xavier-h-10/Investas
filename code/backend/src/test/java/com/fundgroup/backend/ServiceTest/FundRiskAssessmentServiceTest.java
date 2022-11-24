package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dao.FundRiskAssessmentDao;
import com.fundgroup.backend.serviceImpl.FundRiskAssessmentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FundRiskAssessmentServiceTest {
	@Mock
	private FundRiskAssessmentDao fundRiskAssessmentDao;

	@InjectMocks
	FundRiskAssessmentServiceImpl fundRiskAssessmentService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAll() {
		fundRiskAssessmentService.getAll();
	}
}
