package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dao.StockBasicInfoDao;
import com.fundgroup.backend.dao.StockDailyInfoDao;
import com.fundgroup.backend.entity.StockBasicInfo;
import com.fundgroup.backend.entity.StockDailyInfo;
import com.fundgroup.backend.serviceImpl.StockDailyInfoServiceImpl;
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

public class StockDailyInfoServiceTest {
	@Mock
	private StockDailyInfoDao stockDailyInfoDao;
	@Mock
	private StockBasicInfoDao stockBasicInfoDao;

	@InjectMocks
	StockDailyInfoServiceImpl stockDailyInfoService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getStockDailyTimeRange() {
		stockDailyInfoService.getStockDailyTimeRange("1", TimeType.FROM_THIS_YEAR);
	}

	@Test
	public void missingDateInterpolation0() {
		Mockito.when(stockBasicInfoDao.getAllStockBasic()).thenReturn(new ArrayList<>());
		stockDailyInfoService.missingDateInterpolation();
	}

	@Test
	public void missingDateInterpolation1() {
		List<StockBasicInfo> stockBasicInfos = new ArrayList<>();
		StockBasicInfo stockBasicInfo = new StockBasicInfo();
		stockBasicInfo.setStockId("1");
		stockBasicInfos.add(stockBasicInfo);
		Mockito.when(stockBasicInfoDao.getAllStockBasic()).thenReturn(stockBasicInfos);

		StockDailyInfo stockDailyInfo0 = new StockDailyInfo();
		stockDailyInfo0.setUpdateDate(LocalDate.of(2021, 9, 10));
		Mockito.when(stockDailyInfoDao.findFirstByStockIdOrderByUpdateDate("1")).thenReturn(stockDailyInfo0);

		StockDailyInfo stockDailyInfo1 = new StockDailyInfo("1", LocalDate.now(), 1D, 1D,
				1D);
		Mockito.when(stockDailyInfoDao.getStockDailyByCodeDate("1",
				LocalDate.of(2021, 9, 10))).thenReturn(stockDailyInfo1);

		Mockito.when(stockDailyInfoDao.getStockDailyByCodeDate("1",
				LocalDate.of(2021, 9, 10).plusDays(1))).thenReturn(null);


		stockDailyInfoService.missingDateInterpolation();
	}

	@Test
	public void missingDateInterpolation2() {
		List<StockBasicInfo> stockBasicInfos = new ArrayList<>();
		StockBasicInfo stockBasicInfo = new StockBasicInfo();
		stockBasicInfo.setStockId("1");
		stockBasicInfos.add(stockBasicInfo);
		Mockito.when(stockBasicInfoDao.getAllStockBasic()).thenReturn(stockBasicInfos);

		StockDailyInfo stockDailyInfo0 = new StockDailyInfo();
		stockDailyInfo0.setUpdateDate(LocalDate.of(2021, 9, 10));
		Mockito.when(stockDailyInfoDao.findFirstByStockIdOrderByUpdateDate("1")).thenReturn(stockDailyInfo0);

		StockDailyInfo stockDailyInfo1 = new StockDailyInfo("1", LocalDate.now(), 1D, 1D,
				1D);
		Mockito.when(stockDailyInfoDao.getStockDailyByCodeDate("1",
				LocalDate.of(2021, 9, 10))).thenReturn(stockDailyInfo1);

		StockDailyInfo stockDailyInfo2 = new StockDailyInfo();
		Mockito.when(stockDailyInfoDao.getStockDailyByCodeDate("1",
				LocalDate.of(2021, 9, 10).plusDays(1))).thenReturn(stockDailyInfo2);


		stockDailyInfoService.missingDateInterpolation();
	}
}
