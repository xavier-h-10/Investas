package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dao.FundCompetitionDao;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundCompeUser;
import com.fundgroup.backend.entity.FundCompeUserPos;
import com.fundgroup.backend.repository.*;
import com.fundgroup.backend.serviceImpl.FundCompetitionServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

public class FundCompetitionServiceTest {

	@Mock
	FundCompetitionDao fundCompetitionDao;
	@Mock
	FundCompetitionRepository fundCompetitionRepository;
	@Mock
	FundBasicInfoRepository fundBasicInfoRepository;
	@Mock
	FundCompeUserRepository fundCompeUserRepository;
	@Mock
	FundCompeUserPosRepository fundCompeUserPosRepository;
	@Mock
	FundCompeUserPosLogRepository fundCompeUserPosLogRepository;
	@Mock
	FundDailyInfoRepository fundDailyInfoRepository;

	@InjectMocks
	FundCompetitionServiceImpl fundCompetitionService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundCompetitionDao.getAll()).thenReturn(null);
		Mockito.when(fundCompetitionDao.createCompetition(new HashMap<>())).thenReturn(null);
		Mockito.when(fundCompetitionRepository.getActiveCompetitionByUserIdAndFundTypeAndEndDate(1L, 1,
				LocalDate.now())).thenReturn(null);
		Mockito.when(fundCompetitionDao.removeById(1)).thenReturn(null);
		Mockito.when(fundCompeUserPosLogRepository.getFundCompeUserPosLogByCodeAndCompeIdAndUserId("1",
				1, 1L)).thenReturn(null);
		Mockito.when(fundCompeUserPosRepository.getFundCompeUserPosByCodeAndCompeIdAndUserId("1",
				1, 1L)).thenReturn(null);
		Mockito.when(fundCompetitionRepository.getFundCompetitionByCompeId(1)).thenReturn(null);
		Mockito.when(fundCompetitionRepository.getActivePublicCompetition(LocalDate.now())).thenReturn(null);
		Mockito.when(fundCompetitionRepository.getActivePublicCompetitionByUserId(1L, LocalDate.now()))
				.thenReturn(null);
	}

	@Test
	public void testCreateCompetition() {
		Object ret = fundCompetitionService.createCompetition(new HashMap<>());
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionDao).createCompetition(new HashMap<>());
	}

	@Test
	public void testGetAll() {
		Object ret = fundCompetitionService.getAll();
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionDao).getAll();
	}

	@Test
	public void testGetActiveCompetitionByUserIdAndFundTypeAndEndDate() {
		Object ret = fundCompetitionService.getActiveCompetitionByUserIdAndFundTypeAndEndDate(1L, 1,
				LocalDate.now());
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionRepository).getActiveCompetitionByUserIdAndFundTypeAndEndDate(1L, 1,
				LocalDate.now());
	}

	@Test
	public void testRemoveCompetition() {
		Object ret = fundCompetitionService.removeCompetition(1);
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionDao).removeById(1);
	}

	@Test
	public void testGetFundCompeUserPosLogByCodeAndCompeIdAndUserId() {
		Object ret = fundCompetitionService.getFundCompeUserPosLogByCodeAndCompeIdAndUserId("1",1,
				1L);
		Assert.assertNull(ret);
		Mockito.verify(fundCompeUserPosLogRepository).getFundCompeUserPosLogByCodeAndCompeIdAndUserId("1",
				1, 1L);
	}

	@Test
	public void testGetFundCompeUserPosByCodeAndCompeIdAndUserId() {
		Object ret = fundCompetitionService.getFundCompeUserPosByCodeAndCompeIdAndUserId("1",1,
				1L);
		Assert.assertNull(ret);
		Mockito.verify(fundCompeUserPosRepository).getFundCompeUserPosByCodeAndCompeIdAndUserId("1",
				1, 1L);
	}

	@Test
	public void testGetFundCompetitionByCompeId() {
		Object ret = fundCompetitionService.getFundCompetitionByCompeId(1);
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionRepository).getFundCompetitionByCompeId(1);
	}

	@Test
	public void testGetAllActivePublicCompetition() {
		Object ret = fundCompetitionService.getAllActivePublicCompetition(LocalDate.now());
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionRepository).getActivePublicCompetition(LocalDate.now());
	}

	@Test
	public void testGetActivePublicCompetitionByUserId() {
		Object ret = fundCompetitionService.getActivePublicCompetitionByUserId(1L, LocalDate.now());
		Assert.assertNull(ret);
		Mockito.verify(fundCompetitionRepository).getActivePublicCompetitionByUserId(1L, LocalDate.now());
	}

	@Test
	public void buyFund() {
		FundCompeUser fundCompeUser = new FundCompeUser();
		fundCompeUser.setFundCompeUserPosList(new ArrayList<>());
		fundCompeUser.setSurplusMoney(BigDecimal.ONE);

		FundCompeUserPos fundCompeUserPos = new FundCompeUserPos();
		fundCompeUserPos.setAmount(BigDecimal.ONE);

		Mockito.when(fundCompeUserRepository.getFundCompeUserByCompetitionIdAndUserId(1, 1L))
				.thenReturn(fundCompeUser);
		Mockito.when(fundCompeUserPosRepository.getFundCompeUserPosByCodeAndCompeIdAndUserId("1",
				1, 1L)).thenReturn(fundCompeUserPos);
		fundCompetitionService.buyFund(1, 1L, "1", BigDecimal.ONE);

		System.out.println(fundCompeUserPos);

		fundCompetitionService.buyFund(1, 1L, "1", BigDecimal.ONE.negate());

		Mockito.when(fundCompeUserRepository.getFundCompeUserByCompetitionIdAndUserId(1, 1L))
				.thenReturn(fundCompeUser);
		Mockito.when(fundCompeUserPosRepository.getFundCompeUserPosByCodeAndCompeIdAndUserId("1",
				1, 1L)).thenReturn(null);
		fundCompetitionService.buyFund(1, 1L, "1", BigDecimal.ONE);
	}

}
