package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dao.FundCompeUserDao;
import com.fundgroup.backend.dao.FundCompetitionDao;
import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.entity.FundCompeUser;
import com.fundgroup.backend.entity.FundCompetition;
import com.fundgroup.backend.service.FundCompeUserService;
import com.fundgroup.backend.serviceImpl.FundCompeUserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

public class FundCompeUserServiceTest {

	@Mock
	private FundCompeUserDao fundCompeUserDao;
	@Mock
	private FundDailyInfoDao fundDailyInfoDao;
	@Mock
	private FundCompetitionDao fundCompetitionDao;

	@InjectMocks
	private FundCompeUserServiceImpl fundCompeUserService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		FundCompetition fundCompetition = new FundCompetition();
		FundCompetition fundCompetition1 = new FundCompetition();
		fundCompetition.setCompetitionId(1);
		fundCompetition.setNumber(BigInteger.ZERO);
		fundCompetition.setCapacity(BigInteger.ONE);
		fundCompetition.setInitialCapital(BigInteger.ONE);
		fundCompetition1.setCompetitionId(1);
		fundCompetition1.setNumber(BigInteger.ONE);
		fundCompetition1.setCapacity(BigInteger.ONE);
		fundCompetition1.setInitialCapital(BigInteger.ONE);
		FundCompeUser fundCompeUser = new FundCompeUser();
		Mockito.when(fundCompetitionDao.getActivePublicCompetitionByCompeId(1))
				.thenReturn(fundCompetition);
		Mockito.when(fundCompetitionDao.getActivePublicCompetitionByCompeId(2))
				.thenReturn(null);
		Mockito.when(fundCompetitionDao.getActivePublicCompetitionByCompeId(3))
				.thenReturn(fundCompetition1);
		Mockito.when(fundCompeUserDao.getFundCompeUserByCompetitionIdAndUserId(1, 1L))
				.thenReturn(null);
		Mockito.when(fundCompeUserDao.getFundCompeUserByCompetitionIdAndUserId(1, 2L))
				.thenReturn(fundCompeUser);
		Mockito.when(fundCompeUserDao.getAllByUserId(1L)).thenReturn(null);
		Mockito.when(fundCompeUserDao.getAllActiveByUserId(1L)).thenReturn(null);
		Mockito.when(fundCompeUserDao.getFundCompeUserByCompetitionIdAndUserId(1, 1L)).thenReturn(null);
	}

	@Test
	public void testGetAll() {
		Object ret = fundCompeUserService.getAll(1L);
		Assert.assertNull(ret);
		Mockito.verify(fundCompeUserDao).getAllByUserId(1L);
	}

	@Test
	public void testGetActive() {
		Object ret = fundCompeUserService.getActive(1L);
		Assert.assertNull(ret);
		Mockito.verify(fundCompeUserDao).getAllActiveByUserId(1L);
	}

	@Test
	public void testGetFundCompeUserByCompetitionIdAndUserId() {
		Object ret = fundCompeUserService.getFundCompeUserByCompetitionIdAndUserId(1, 1L);
		Assert.assertNull(ret);
		Mockito.verify(fundCompeUserDao).getFundCompeUserByCompetitionIdAndUserId(1, 1L);
	}

	@Test
	public void testJoinCompetition0() {
		FundCompeUser fundCompeUser = new FundCompeUser(1, 1L, null, null, null, false);
		Integer ret = fundCompeUserService.joinCompetition(fundCompeUser);
		Assert.assertEquals(Integer.valueOf(0), ret);
	}

	@Test
	public void testJoinCompetition1() {
		FundCompeUser fundCompeUser = new FundCompeUser(2, 1L, null, null, null, false);
		Integer ret = fundCompeUserService.joinCompetition(fundCompeUser);
		Assert.assertEquals(Integer.valueOf(-2), ret);
	}

	@Test
	public void testJoinCompetition2() {
		FundCompeUser fundCompeUser = new FundCompeUser(3, 1L, null, null, null, false);
		Integer ret = fundCompeUserService.joinCompetition(fundCompeUser);
		Assert.assertEquals(Integer.valueOf(-3), ret);
	}

	@Test
	public void testJoinCompetition3() {
		FundCompeUser fundCompeUser = new FundCompeUser(1, 2L, null, null, null, false);
		Integer ret = fundCompeUserService.joinCompetition(fundCompeUser);
		Assert.assertEquals(Integer.valueOf(-1), ret);
	}

}
