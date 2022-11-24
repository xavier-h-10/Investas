package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dao.StockBasicInfoDao;
import com.fundgroup.backend.serviceImpl.StockServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StockServiceTest {
	@Mock
	private StockBasicInfoDao stockBasicInfoDao;

	@InjectMocks
	StockServiceImpl stockService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getStockBasicInfoByStockId() {
		stockService.getStockBasicInfoByStockId("1");
	}
}
