package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dao.FundManagerInfoDao;
import com.fundgroup.backend.serviceImpl.FundManagerInfoServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FundManagerInfoServiceTest {
	@Mock
	FundManagerInfoDao fundManagerInfoDao;

	@InjectMocks
	FundManagerInfoServiceImpl fundManagerInfoService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getFundManagerInfoById() {
		Object ret = fundManagerInfoService.getFundManagerInfoById("1");
	}


}
