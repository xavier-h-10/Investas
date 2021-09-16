package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.FundManagerInfoDaoImpl;
import com.fundgroup.backend.dto.ManagerInfo;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import com.fundgroup.backend.repository.FundManagerInfoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class FundManagerInfoDaoTest {
	@Mock
	FundManagerInfoRepository fundManagerInfoRepository;
	@Mock
	FundBasicInfoRepository fundBasicInfoRepository;

	@InjectMocks
	FundManagerInfoDaoImpl fundManagerInfoDao;

	ManagerInfo managerInfo;

	@Before
	public void setUp() throws Exception {
		managerInfo = new ManagerInfo("1", "1", LocalDate.now(), "1", "");
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundManagerInfoRepository.getManagerInfoDtoById("123")).thenReturn(null);
		Mockito.when(fundManagerInfoRepository.getManagerInfoDtoById("233")).thenReturn(managerInfo);
		Mockito.when(fundBasicInfoRepository.getFundByManagerId("233")).thenReturn(null);
		managerInfo.setFundList(null);
	}

	@Test
	public void testGetFundManagerInfoByIdNull() {
		ManagerInfo ret = fundManagerInfoDao.getFundManagerInfoById("123");
		Assert.assertNull(ret);
	}

	@Test
	public void testGetFundManagerInfoByIdNotNull() {
		ManagerInfo ret = fundManagerInfoDao.getFundManagerInfoById("233");
		Assert.assertEquals(managerInfo, ret);
	}
}
