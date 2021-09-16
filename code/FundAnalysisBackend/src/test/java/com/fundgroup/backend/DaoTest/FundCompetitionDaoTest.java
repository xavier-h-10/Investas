package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.FundCompetitionDaoImpl;
import com.fundgroup.backend.entity.FundCompetition;
import com.fundgroup.backend.repository.FundCompetitionRepository;
import com.fundgroup.backend.security.UserInfoHelper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

public class FundCompetitionDaoTest {
	@Mock
	FundCompetitionRepository fundCompetitionRepository;
	@Mock
	UserInfoHelper userInfoHelper;

	@InjectMocks
	private FundCompetitionDaoImpl fundCompetitionDao;

	private FundCompetition fundCompetition;

	@Before
	public void setUp() throws Exception {
		fundCompetition = new FundCompetition(
				Long.valueOf("1"), "测试", LocalDate.parse("2021-09-01"), LocalDate.parse("2021-09-20")
				, "测试", BigInteger.valueOf(2), BigInteger.valueOf(10000), 2,
				false);
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundCompetitionRepository.saveAndFlush(any(FundCompetition.class))).thenReturn(fundCompetition);
		Mockito.when(fundCompetitionRepository.findAll()).thenReturn(null);
		Mockito.when(fundCompetitionRepository.getActivePublicCompetition(LocalDate.now())).thenReturn(null);
		Mockito.when(fundCompetitionRepository.getActiveCompetition(LocalDate.now())).thenReturn(null);
		Mockito.when(fundCompetitionRepository.getFundCompetitionByCompeId(1)).thenReturn(null);
		Mockito.when(fundCompetitionRepository.getActivePublicCompetitionByCompeId(1, LocalDate.now()))
				.thenReturn(null);
	}

	@Test
	public void testCreateCompetition0(){
		Mockito.when(userInfoHelper.getUserId()).thenReturn(Long.valueOf(1));
		Map<String, Object> args = new HashMap<>();
		args.put("title", "测试");
		args.put("goal", "测试");
		args.put("people", BigInteger.valueOf(2));
		args.put("initial", BigInteger.valueOf(10000));
		args.put("date", "[2021-09-01, 2021-09-20]");
		args.put("select-type", "[T1]");
		args.put("publicType", "0");
		Integer ret = fundCompetitionDao.createCompetition(args);
		System.out.println(ret);
		Mockito.verify(fundCompetitionRepository).saveAndFlush(fundCompetition);
	}

	@Test
	public void testCreateCompetition1(){
		Mockito.when(userInfoHelper.getUserId()).thenReturn(Long.valueOf(-1));
		Map<String, Object> args = new HashMap<>();
		args.put("title", "测试");
		args.put("goal", "测试");
		args.put("people", BigInteger.valueOf(2));
		args.put("initial", BigInteger.valueOf(10000));
		args.put("date", "[2021-09-01, 2021-09-20]");
		args.put("select-type", "[T1, T6]");
		args.put("publicType", "0");
		Integer ret = fundCompetitionDao.createCompetition(args);
	}

	@Test
	public void testCreateCompetition2(){
		Mockito.when(userInfoHelper.getUserId()).thenReturn(Long.valueOf(1));
		Map<String, Object> args = new HashMap<>();
		args.put("title", "测试");
		args.put("goal", "测试");
		args.put("people", BigInteger.valueOf(2));
		args.put("initial", BigInteger.valueOf(10000));
		args.put("date", "[2021-09-01, 2021-09-20]");
		args.put("select-type", "[T6]");
		args.put("publicType", "0");
		Integer ret = fundCompetitionDao.createCompetition(args);
	}

	@Test
	public void testGetAll() {
		List<FundCompetition> ret = fundCompetitionDao.getAll();
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionRepository).findAll();
	}

	@Test
	public void testRemoveById() {
		List<FundCompetition> ret = fundCompetitionDao.removeById(0);
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionRepository).findAll();
	}

	@Test
	public void testGetActivePublicCompetition() {
		List<FundCompetition> ret = fundCompetitionDao.getActivePublicCompetition(LocalDate.now());
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionRepository).getActivePublicCompetition(LocalDate.now());
	}

	@Test
	public void testGetActiveCompetition() {
		List<FundCompetition> ret = fundCompetitionDao.getActiveCompetition(LocalDate.now());
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionRepository).getActiveCompetition(LocalDate.now());
	}

	@Test
	public void testGetFundCompetitionByCompeId() {
		FundCompetition ret = fundCompetitionDao.getFundCompetitionByCompeId(1);
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionRepository).getFundCompetitionByCompeId(1);
	}

	@Test
	public void testGetActivePublicCompetitionByCompeId() {
		FundCompetition ret = fundCompetitionDao.getActivePublicCompetitionByCompeId(1);
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionRepository).getActivePublicCompetitionByCompeId(1, LocalDate.now());
	}
}
