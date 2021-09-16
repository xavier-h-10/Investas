package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.daoImpl.FundBasicInfoDaoImpl;
import com.fundgroup.backend.daoImpl.FundCompetitionDaoImpl;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundCompetition;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import com.fundgroup.backend.utils.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class FundBasicInfoDaoTest {

	@Mock
	private FundBasicInfoRepository fundBasicInfoRepository;

	@InjectMocks
	private FundBasicInfoDaoImpl fundBasicInfoDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundBasicInfoRepository.searchFundByCodeOrName("a", PageRequest.of(0, 1)))
				.thenReturn(null);
		Mockito.when(fundBasicInfoRepository.getAllCode()).thenReturn(null);
		Mockito.when(fundBasicInfoRepository.getAllFund()).thenReturn(null);
		Mockito.when(fundBasicInfoRepository.getById("000689")).thenReturn(null);
		Mockito.when(fundBasicInfoRepository.countFundNumTimeRange(
				DateUtils.minusDayByTimeType(LocalDate.now(), TimeType.FROM_THIS_YEAR),
				LocalDate.now())).thenReturn(null);
		Mockito.when(fundBasicInfoRepository.getAllFundInfo()).thenReturn(null);
		Mockito.when(fundBasicInfoRepository.getFundNumber()).thenReturn(null);
	}

	@Test
	public void testSearchFundByCodeOrName() {
		List<FundBasicInfo> ret = fundBasicInfoDao.searchFundByCodeOrName("a", 0, 1);
		Assert.assertNull(ret);
		Mockito.verify(fundBasicInfoRepository).searchFundByCodeOrName("a", PageRequest.of(0, 1));
	}

	@Test
	public void testGetAllCode() {
		List<String> ret = fundBasicInfoDao.getAllCode();
		Assert.assertNull(ret);
		Mockito.verify(fundBasicInfoRepository).getAllCode();
	}

	@Test
	public void testGetAllFund() {
		List<FundBasicInfo> ret = fundBasicInfoDao.getAllFund();
		Assert.assertNull(ret);
		Mockito.verify(fundBasicInfoRepository).getAllFund();
	}

	@Test
	public void testGetOne() {
		FundBasicInfo ret = fundBasicInfoDao.getOne("000689");
		Assert.assertNull(ret);
		Mockito.verify(fundBasicInfoRepository).getById("000689");
	}

	@Test
	public void testCountFundNumTimeRange() {
		Integer ret = fundBasicInfoDao.countFundNumTimeRange(TimeType.FROM_THIS_YEAR);
		Assert.assertNull(ret);
		Mockito.verify(fundBasicInfoRepository).countFundNumTimeRange(
				DateUtils.minusDayByTimeType(LocalDate.now(), TimeType.FROM_THIS_YEAR),
				LocalDate.now());
	}

	@Test
	public void testGetAllFundInfo() {
		List<Object> ret = fundBasicInfoDao.getAllFundInfo();
		Assert.assertNull(ret);
		Mockito.verify(fundBasicInfoRepository).getAllFundInfo();
	}

	@Test
	public void testGetFundNumber() {
		Integer ret = fundBasicInfoDao.getFundNumber();
		Assert.assertNull(ret);
		Mockito.verify(fundBasicInfoRepository).getFundNumber();
	}
}
