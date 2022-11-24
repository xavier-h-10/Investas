package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.dao.UserPositionDao;
import com.fundgroup.backend.serviceImpl.UserPositionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserPositionServiceTest {
	@Mock
	UserPositionDao userPositionDao;
	@Mock
	FundDailyInfoDao fundDailyInfoDao;

	@InjectMocks
	UserPositionServiceImpl userPositionService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void setPositionByMoney() {
		Mockito.when(fundDailyInfoDao.getNewestNAVs("1", 1)).thenReturn(null);
		List<Double> NAVs = new ArrayList<>();
		NAVs.add(1D);
		Mockito.when(fundDailyInfoDao.getNewestNAVs("2", 1)).thenReturn(NAVs);
		userPositionService.setPositionByMoney(1L, "1", 1D);
		userPositionService.setPositionByMoney(1L, "2", 1D);
	}

	@Test
	public void setPositionByAmount() {
		Mockito.when(fundDailyInfoDao.getNewestNAVs("1", 1)).thenReturn(null);
		List<Double> NAVs = new ArrayList<>();
		NAVs.add(1D);
		Mockito.when(fundDailyInfoDao.getNewestNAVs("2", 1)).thenReturn(NAVs);
		userPositionService.setPositionByAmount(1L, "1", 1D);
		userPositionService.setPositionByAmount(1L, "2", 1D);
	}

	@Test
	public void simpleTest() {
		userPositionService.getPositions(1L);
		userPositionService.getUserPositionByUserIdAndFundCode(1L, "1");
		userPositionService.delUserPositionByUserIdAndFundCode(1L, "1");
	}
}
