package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundDailyInfo;
import com.fundgroup.backend.serviceImpl.FundDailyInfoServiceImpl;
import com.fundgroup.backend.utils.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FundDailyInfoServiceTest {
	@Mock
	private FundDailyInfoDao fundDailyInfoDao;
	@Mock
	private FundBasicInfoDao fundBasicInfoDao;

	@InjectMocks
	FundDailyInfoServiceImpl fundDailyInfoService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundDailyInfoDao.getFundDailyRangeSimp("1",
						DateUtils.minusDayByTimeType(LocalDate.now(), TimeType.FROM_THIS_YEAR), LocalDate.now()))
				.thenReturn(null);
		Mockito.when(fundDailyInfoDao.getFundDailyPage("1", 0, 1)).thenReturn(null);
		Mockito.when(fundDailyInfoDao.getFundDailyDetailFourByCode("1", 0, 1)).thenReturn(null);
		Mockito.when(fundDailyInfoDao.getDailyInfoCountByUpdateDate(LocalDate.now())).thenReturn(1);
//		Mockito.when(fundBasicInfoDao.getFundNumber()).thenReturn(1);
	}

	@Test
	public void testGetFundDailyTimeRange() {
		Object ret = fundDailyInfoService.getFundDailyTimeRange("1", TimeType.FROM_THIS_YEAR);
		Assert.assertNull(ret);
		Mockito.verify(fundDailyInfoDao).getFundDailyRangeSimp("1",
				DateUtils.minusDayByTimeType(LocalDate.now(), TimeType.FROM_THIS_YEAR), LocalDate.now());
	}

	@Test
	public void testGetFundDailyPage() {
		Object ret = fundDailyInfoService.getFundDailyPage("1", 0, 1);
		Assert.assertNull(ret);
		Mockito.verify(fundDailyInfoDao).getFundDailyPage("1", 0, 1);
	}

	@Test
	public void testGetFundDailyDetailFourPage() {
		Object ret = fundDailyInfoService.getFundDailyDetailFourPage("1", 0, 1);
		Assert.assertNull(ret);
		Mockito.verify(fundDailyInfoDao).getFundDailyDetailFourByCode("1", 0, 1);
	}

	@Test
	public void testCheckDailyUpdateComplete0() {
		Mockito.when(fundBasicInfoDao.getFundNumber()).thenReturn(1);
		Object ret = fundDailyInfoService.checkDailyUpdateComplete();
		Assert.assertEquals(true, ret);
	}

	@Test
	public void testCheckDailyUpdateComplete1() {
		Mockito.when(fundBasicInfoDao.getFundNumber()).thenReturn(2);
		Object ret = fundDailyInfoService.checkDailyUpdateComplete();
		Assert.assertEquals(false, ret);
	}

	@Test
	public void testMissingDateInterpolation()
	{
		fundDailyInfoService.missingDateInterpolation();
	}
}
