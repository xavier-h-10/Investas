package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.StockBasicInfoDaoImpl;
import com.fundgroup.backend.repository.StockBasicInfoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class StockBasicInfoDaoTest {
	@Mock
	private StockBasicInfoRepository stockBasicInfoRepository;

	@InjectMocks
	StockBasicInfoDaoImpl stockBasicInfoDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(stockBasicInfoRepository.getStockBasicInfoByStockId("1")).thenReturn(null);
		Mockito.when(stockBasicInfoRepository.getAllStockBasic()).thenReturn(null);
	}

	@Test
	public void testGetStockBasicInfoByStockId() {
		Object ret = stockBasicInfoDao.getStockBasicInfoByStockId("1");
		Assert.assertNull(ret);
		Mockito.verify(stockBasicInfoRepository).getStockBasicInfoByStockId("1");
	}

	@Test
	public void testGetAllStockBasic() {
		Object ret = stockBasicInfoDao.getAllStockBasic();
		Assert.assertNull(ret);
		Mockito.verify(stockBasicInfoRepository).getAllStockBasic();
	}
}
