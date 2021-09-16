package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.cache.FundAssemblyCache;
import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dto.FundAssembly;
import com.fundgroup.backend.dto.FundCard;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.serviceImpl.FundBasicInfoServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class FundBasicInfoServiceTest {
	@Mock
	private FundBasicInfoDao fundBasicInfoDao;
	@Mock
	private FundAssemblyCache fundAssemblyCache;

	@InjectMocks
	private FundBasicInfoServiceImpl fundBasicInfoService;

	@Before
	public void setUpMock() throws Exception {
		List<FundBasicInfo> fundBasicInfoList = new ArrayList<>();
		FundBasicInfo fundBasicInfo = new FundBasicInfo("000001");
		fundBasicInfoList.add(fundBasicInfo);
		MockitoAnnotations.openMocks(this);
		Mockito.when(fundBasicInfoDao.searchFundByCodeOrName("000001", 0, 20))
				.thenReturn(fundBasicInfoList);
		FundAssembly fundAssembly = new FundAssembly("000001");
		fundAssembly.setFundCode("000001");
		Mockito.when(fundAssemblyCache.getFundAssembly("000001")).thenReturn(fundAssembly);
	}

	@Test
	public void testSearchFundCardByCode() {
		FundCard ret = fundBasicInfoService.searchFundCardByCode("000001");
		Assert.assertNotNull(ret);
		Mockito.verify(fundAssemblyCache).getFundAssembly("000001");
	}

	@Test
	public void test() {
		List<FundBasicInfo> fundBasicInfos = fundBasicInfoService.searchFundByCodeOrName("000001", 0, 20);
		Assert.assertEquals(fundBasicInfos.get(0).getFundCode(), "000001");
		Mockito.verify(fundBasicInfoDao).searchFundByCodeOrName("000001", 0, 20);
	}
}
