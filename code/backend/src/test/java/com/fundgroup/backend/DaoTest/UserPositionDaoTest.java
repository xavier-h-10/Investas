package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.UserPositionDaoImpl;
import com.fundgroup.backend.entity.UserPosition;
import com.fundgroup.backend.repository.UserPositionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class UserPositionDaoTest {
	@Mock
	UserPositionRepository userPositionRepository;

	@InjectMocks
	UserPositionDaoImpl userPositionDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(userPositionRepository.getUserPositionByUserIdAndFundCode(1L, "1"))
				.thenReturn(null);
		Mockito.when(userPositionRepository.getUserPositionByUserIdAndFundCode(2L, "2"))
				.thenReturn(new UserPosition());
		Mockito.when(userPositionRepository.findAllByUserIdAndHoldingEndDate(1L, LocalDate.now()))
				.thenReturn(null);
	}

	@Test
	public void testSaveNull() {
		userPositionDao.save(1L, "1", LocalDate.now(), 1.0D, 1.0);
	}

	@Test
	public void testSaveNotNull() {
		userPositionDao.save(2L, "2", LocalDate.now(), 1.0D, 1.0);
	}

	@Test
	public void testGetPositions() {
		Object ret = userPositionDao.getPositions(LocalDate.now(), 1L);
		Assert.assertNull(ret);
		Mockito.verify(userPositionRepository).findAllByUserIdAndHoldingEndDate(1L, LocalDate.now());
	}

	@Test
	public void testGetUserPositionByUserIdAndFundCode() {
		Object ret = userPositionDao.getUserPositionByUserIdAndFundCode(1L, "1");
		Assert.assertNull(ret);
		Mockito.verify(userPositionRepository).getUserPositionByUserIdAndFundCode(1L, "1");
	}

	@Test
	public void testDelUserPositionByUserIdAndFundCodeNull() {
		Object ret = userPositionDao.delUserPositionByUserIdAndFundCode(1L, "1");
	}

	@Test
	public void testDelUserPositionByUserIdAndFundCodeNotNull() {
		Object ret = userPositionDao.delUserPositionByUserIdAndFundCode(2L, "2");
	}
}
