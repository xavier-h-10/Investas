package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.StockDailyInfoDaoImpl;
import com.fundgroup.backend.entity.StockDailyInfo;
import com.fundgroup.backend.repository.StockDailyInfoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class StockDailyInfoDaoTest {
	@Mock
	private StockDailyInfoRepository stockDailyInfoRepository;

	@InjectMocks
	StockDailyInfoDaoImpl stockDailyInfoDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(stockDailyInfoRepository.getAllByStockId("1")).thenReturn(null);
		Mockito.when(stockDailyInfoRepository.getStockDailyRangeSimp("1", LocalDate.now(), LocalDate.now()))
				.thenReturn(null);
		Mockito.when(stockDailyInfoRepository.getStockDailyByCodeDate("1", LocalDate.now()))
				.thenReturn(null);
		Mockito.when(stockDailyInfoRepository.findFirstByStockIdOrderByUpdateDate("1")).thenReturn(null);
	}

	@Test
	public void testGetAllByStockId() {
		Object ret = stockDailyInfoDao.getAllByStockId("1");
		Assert.assertNull(ret);
		Mockito.verify(stockDailyInfoRepository).getAllByStockId("1");
	}

	@Test
	public void testGetStockDailyRangeSimp() {
		Object ret = stockDailyInfoDao.getStockDailyRangeSimp("1", LocalDate.now(), LocalDate.now());
		Assert.assertNull(ret);
		Mockito.verify(stockDailyInfoRepository).getStockDailyRangeSimp("1", LocalDate.now(), LocalDate.now());
	}

	@Test
	public void testGetStockDailyByCodeDate() {
		Object ret = stockDailyInfoDao.getStockDailyByCodeDate("1", LocalDate.now());
		Assert.assertNull(ret);
		Mockito.verify(stockDailyInfoRepository).getStockDailyByCodeDate("1", LocalDate.now());
	}

	@Test
	public void testFindFirstByStockIdOrderByUpdateDate() {
		Object ret = stockDailyInfoDao.findFirstByStockIdOrderByUpdateDate("1");
		Assert.assertNull(ret);
		Mockito.verify(stockDailyInfoRepository).findFirstByStockIdOrderByUpdateDate("1");
	}

	@Test
	public void testSaveStockDailyInfo() {
		stockDailyInfoDao.saveStockDailyInfo(new StockDailyInfo());
	}
}
