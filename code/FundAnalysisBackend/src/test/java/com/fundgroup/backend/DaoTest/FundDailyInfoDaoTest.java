package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.FundDailyInfoDaoImpl;
import com.fundgroup.backend.entity.FundCompeUser;
import com.fundgroup.backend.entity.FundDailyInfo;
import com.fundgroup.backend.repository.FundDailyInfoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

public class FundDailyInfoDaoTest {
	@Mock
	private FundDailyInfoRepository fundDailyInfoRepository;

	@InjectMocks
	private FundDailyInfoDaoImpl fundDailyInfoDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundDailyInfoRepository.getFundDailyInfoByCodeDate("000689", LocalDate.now()))
				.thenReturn(null);
		Mockito.when(fundDailyInfoRepository.getFundDailyRangeSimp("000689", LocalDate.now(), LocalDate.now()))
				.thenReturn(null);
		Mockito.when(fundDailyInfoRepository.getFundDailyInfoByCode("000689", PageRequest.of(0, 1)))
				.thenReturn(null);
		Mockito.when(fundDailyInfoRepository.getFundDailyDetailFourByCode("000689",
				PageRequest.of(0, 1))).thenReturn(null);
		Mockito.when(fundDailyInfoRepository.getFundDailyRange("000689", LocalDate.now(), LocalDate.now()))
				.thenReturn(null);
		Mockito.when(fundDailyInfoRepository.getNewestNAVs("000689", 1)).thenReturn(null);
		Mockito.when(fundDailyInfoRepository.findFirstByFundCodeOrderByUpdateDate("000689")).thenReturn(null);
		Mockito.when(fundDailyInfoRepository.save(new FundDailyInfo())).thenReturn(null);
		Mockito.when(fundDailyInfoRepository.getDailyInfoCountByUpdateDate(LocalDate.now())).thenReturn(null);
	}

	@Test
	public void testGetFundCompeUsersByCompetitionId() {
		Object ret = fundDailyInfoDao.getFundDailyInfoByCodeDate("000689", LocalDate.now());
		Assert.assertNull(ret);
		Mockito.verify(fundDailyInfoRepository).getFundDailyInfoByCodeDate("000689", LocalDate.now());
	}

	@Test
	public void testGetFundDailyRangeSimp() {
		Object ret = fundDailyInfoDao.getFundDailyRangeSimp("000689", LocalDate.now(), LocalDate.now());
		Assert.assertNull(ret);
		Mockito.verify(fundDailyInfoRepository).getFundDailyRangeSimp("000689", LocalDate.now(),
				LocalDate.now());
	}

	@Test
	public void testGetFundDailyPage() {
		Object ret = fundDailyInfoDao.getFundDailyPage("000689", 0, 1);
		Assert.assertNull(ret);
		Mockito.verify(fundDailyInfoRepository).getFundDailyInfoByCode("000689", PageRequest.of(0, 1));
	}

	@Test
	public void testGetFundDailyDetailFourByCode() {
		Object ret = fundDailyInfoDao.getFundDailyDetailFourByCode("000689", 0, 1);
		Assert.assertNull(ret);
		Mockito.verify(fundDailyInfoRepository).getFundDailyDetailFourByCode("000689", PageRequest.of(0, 1));
	}

	@Test
	public void testGetFundDailyRange() {
		Object ret = fundDailyInfoDao.getFundDailyRange("000689", LocalDate.now(), LocalDate.now());
		Assert.assertNull(ret);
		Mockito.verify(fundDailyInfoRepository).getFundDailyRange("000689", LocalDate.now(),
				LocalDate.now());
	}

	@Test
	public void testGetNewestNAVs() {
		Object ret = fundDailyInfoDao.getNewestNAVs("000689", 1);
		Assert.assertNull(ret);
		Mockito.verify(fundDailyInfoRepository).getNewestNAVs("000689", 1);
	}

	@Test
	public void testGetFurthestRecord() {
		Object ret = fundDailyInfoDao.getFurthestRecord("000689");
		Assert.assertNull(ret);
		Mockito.verify(fundDailyInfoRepository).findFirstByFundCodeOrderByUpdateDate("000689");
	}

	@Test
	public void testSaveFundDailyInfo() {
		Object ret = fundDailyInfoDao.saveFundDailyInfo(new FundDailyInfo());
		Assert.assertNull(ret);
		Mockito.verify(fundDailyInfoRepository).save(new FundDailyInfo());
	}

	@Test
	public void testGetDailyInfoCountByUpdateDate() {
		Object ret = fundDailyInfoDao.getDailyInfoCountByUpdateDate(LocalDate.now());
		Assert.assertNull(ret);
		Mockito.verify(fundDailyInfoRepository).getDailyInfoCountByUpdateDate(LocalDate.now());
	}
}
