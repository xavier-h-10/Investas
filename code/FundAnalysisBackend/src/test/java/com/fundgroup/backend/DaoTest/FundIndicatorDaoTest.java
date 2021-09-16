package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dao.FundIndicatorDao;
import com.fundgroup.backend.daoImpl.FundIndicatorDaoImpl;
import com.fundgroup.backend.repository.FundIndicatorAverageRepository;
import com.fundgroup.backend.repository.FundIndicatorRepository;
import com.fundgroup.backend.utils.BatchUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

public class FundIndicatorDaoTest {

	@Mock
	private FundIndicatorRepository fundIndicatorRepository;

	@InjectMocks
	FundIndicatorDaoImpl fundIndicatorDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundIndicatorRepository.getHomeFundIndicator(1, 1, 1, 1,
						PageRequest.of(0, 1))).thenReturn(null);
	}

	@Test
	public void testGetHomeFundIndicator() {
		Object ret = fundIndicatorDao.getHomeFundIndicator(1, 1, 1, 1, 0, 1);
		Assert.assertNull(ret);
		Mockito.verify(fundIndicatorRepository).getHomeFundIndicator(1, 1, 1, 1,
				PageRequest.of(0, 1));
	}
}
