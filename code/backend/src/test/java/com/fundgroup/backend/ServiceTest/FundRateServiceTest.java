package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.dao.FundRateDao;
import com.fundgroup.backend.dto.FundRateRecent;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundDailyInfo;
import com.fundgroup.backend.entity.FundRate;
import com.fundgroup.backend.serviceImpl.FundRateServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class FundRateServiceTest {
	@Mock
	private FundBasicInfoDao fundBasicInfoDao;

	@Mock
	private FundDailyInfoDao fundDailyInfoDao;

	@Mock
	private FundRateDao fundRateDao;

	@InjectMocks
	private FundRateServiceImpl fundRateService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundRateDao.getFundRecRate("1")).thenReturn(new FundRateRecent());
		Mockito.when(fundRateDao.getFundRateByCode("1")).thenReturn(new FundRate());
		Mockito.when(fundRateDao.getDailyInfoCountByUpdateDate(LocalDate.now())).thenReturn(1);
	}

	@Test
	public void updateFundRate() {
		List<FundBasicInfo> fundList = fundBasicInfoDao.getAllFund();
		System.out.println(fundList.size());
	}

	@Test
	public void getFundRecRate() {
		fundRateService.getFundRecRate("1");
	}

	@Test
	public void getFundRateByCode() {
		fundRateService.getFundRateByCode("1");
	}

	@Test
	public void getFundByRate() {
		List<FundRate> fundRates = new ArrayList<>();
		fundRates.add(new FundRate("1", LocalDateTime.now(), 1.0D, 1.0D,
				1.0D, 1.0D, 1.0D, 1.0D, 1.0D,
				1.0D, 1.0D, 1.0D, 1.0D));
		Mockito.when(fundRateDao.getTopFundRateByMonth()).thenReturn(fundRates);
		Mockito.when(fundRateDao.getTopFundRateByYear()).thenReturn(fundRates);
		FundBasicInfo fundBasicInfo = new FundBasicInfo();
		fundBasicInfo.setFundCode("1");
		fundBasicInfo.setFundType(1);
		fundBasicInfo.setFundName("1");
		Mockito.when(fundBasicInfoDao.getOne("1")).thenReturn(fundBasicInfo);
		fundRateService.getFundByRate(0);
		fundRateService.getFundByRate(1);
		fundRateService.getFundByRate(2);
	}

	@Test
	public void checkDailyUpdateComplete() {
		Mockito.when(fundBasicInfoDao.getFundNumber()).thenReturn(1);
		fundRateService.checkDailyUpdateComplete();
		Mockito.when(fundBasicInfoDao.getFundNumber()).thenReturn(2);
		fundRateService.checkDailyUpdateComplete();
	}

	@Test
	public void testUpdateFundRate()
	{
		LocalDate today=LocalDate.now();
		List<FundBasicInfo> fundBasicInfos=new ArrayList<>();
		fundBasicInfos.add(new FundBasicInfo("000001"));
		Mockito.when(fundDailyInfoDao.getDailyInfoCountByUpdateDate(today)).thenReturn(1);
		Mockito.when(fundBasicInfoDao.getFundNumber()).thenReturn(1);
		Mockito.when(fundBasicInfoDao.getAllFund()).thenReturn(fundBasicInfos);
		Mockito.when(fundDailyInfoDao.getFundDailyRange("000001",today.minusDays(1),today)).thenReturn(null);
		fundRateService.updateFundRate(today);
		Double d= Double.valueOf(1);
		Mockito.when(fundDailyInfoDao.getFundDailyInfoByCodeDate("000001", today.minusDays(1))).thenReturn(null);
		Mockito.when(fundDailyInfoDao.getFundDailyInfoByCodeDate("000001", today.minusDays(1))).thenReturn(new FundDailyInfo("000001",d,d,LocalDate.now(),d,d,d,1));
		fundRateService.updateFundRate(today);

	}

	@Test
	public void testUpdateFundRate2()
	{
		LocalDate today=LocalDate.now();
		Double d= Double.valueOf(1);
		List<FundBasicInfo> fundBasicInfos=new ArrayList<>();
		fundBasicInfos.add(new FundBasicInfo("000001"));
		Mockito.when(fundDailyInfoDao.getDailyInfoCountByUpdateDate(today)).thenReturn(1);
		Mockito.when(fundBasicInfoDao.getFundNumber()).thenReturn(1);
		Mockito.when(fundBasicInfoDao.getAllFund()).thenReturn(fundBasicInfos);
		Mockito.when(fundDailyInfoDao.getFundDailyInfoByCodeDate("000001",today)).thenReturn(new FundDailyInfo("000001",d,d,LocalDate.now(),0.0,d,d,1));
		Mockito.when(fundDailyInfoDao.getFundDailyInfoByCodeDate("000001", today.minusDays(1))).thenReturn(null);
		fundRateService.updateFundRate(today);

	}

	@Test
	public void testUpdateFundRate3()
	{
		LocalDate today=LocalDate.now();
		Mockito.when(fundDailyInfoDao.getDailyInfoCountByUpdateDate(today)).thenReturn(1);
		Mockito.when(fundBasicInfoDao.getFundNumber()).thenReturn(10);
		fundRateService.updateFundRate(today);

	}
}
