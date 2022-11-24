package com.fundgroup.backend.ControllerTest;

import com.fundgroup.backend.cache.FundAssemblyCache;
import com.fundgroup.backend.controller.FundCompetitionController;
import com.fundgroup.backend.dto.FundAssembly;
import com.fundgroup.backend.dto.FundCompeRank;
import com.fundgroup.backend.entity.FundCompeUser;
import com.fundgroup.backend.entity.FundCompeUserPos;
import com.fundgroup.backend.entity.FundCompeUserPosLog;
import com.fundgroup.backend.entity.FundCompetition;
import com.fundgroup.backend.security.UserInfoHelper;
import com.fundgroup.backend.service.FundCompeUserService;
import com.fundgroup.backend.service.FundCompetitionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FundCompetitionControllerTest {
	@Mock
	FundCompeUserService fundCompeUserService;
	@Mock
	FundAssemblyCache fundAssemblyCache;
	@Mock
	FundCompetitionService fundCompetitionService;
	@Mock
	UserInfoHelper userInfoHelper;

	@InjectMocks
	FundCompetitionController fundCompetitionController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(userInfoHelper.getUserId()).thenReturn(1L);
	}

	@Test
	public void createCompetition() {
		Map<String, Object> params = new HashMap<>();
		Mockito.when(fundCompetitionService.createCompetition(params)).thenReturn(-1);
		fundCompetitionController.createCompetition(params);
		Mockito.when(fundCompetitionService.createCompetition(params)).thenReturn(1);
		fundCompetitionController.createCompetition(params);
	}

	@Test
	public void getAllCompetition() {
		Mockito.when(fundCompetitionService.getAll()).thenReturn(null);
		fundCompetitionController.getAllCompetition();
	}

	@Test
	public void queryAllCompetitionFromAdmin() {
		Mockito.when(fundCompetitionService.getAll()).thenReturn(null);
		fundCompetitionController.queryAllCompetitionFromAdmin();
	}

	@Test
	public void deleteCompetitionFromAdmin() {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", 123);
		Mockito.when(fundCompetitionService.removeCompetition(123)).thenReturn(null);
		fundCompetitionController.deleteCompetitionFromAdmin(params);
	}

	@Test
	public void getActiveCompetitionsByFundType() {
		Map<String, Object> params = new HashMap<>();
		params.put("type", 1);
		Mockito.when(fundCompetitionService.getActiveCompetitionByUserIdAndFundTypeAndEndDate(1L, 2,
				LocalDate.now())).thenReturn(null);
		fundCompetitionController.getActiveCompetitionsByFundType(params);
	}

	@Test
	public void buyFund() {
		Map<String, Object> params = new HashMap<>();
		params.put("competitionId", 1);
		params.put("fundCode", "1");
		params.put("amount", 1);
		Mockito.when(fundCompetitionService.buyFund(1, 1L, "1", BigDecimal.ONE))
				.thenReturn(null);
		fundCompetitionController.buyFund(params);
		params.put("amount", 1.0D);
		fundCompetitionController.buyFund(params);
		Mockito.when(fundCompetitionService.buyFund(1, 1L, "1", BigDecimal.valueOf(1.0D)))
				.thenReturn("");
		fundCompetitionController.buyFund(params);
	}

	@Test
	public void getFundCompeUserPosLogByCodeAndCompeIdAndUserId() {
		Map<String, Object> params = new HashMap<>();
		params.put("competitionId", 1);
		params.put("fundCode", "1");
		Mockito.when(fundCompetitionService.getFundCompeUserPosLogByCodeAndCompeIdAndUserId("1",
				1, 1L)).thenReturn(null);
		fundCompetitionController.getFundCompeUserPosLogByCodeAndCompeIdAndUserId(params);
		Mockito.when(fundCompetitionService.getFundCompeUserPosLogByCodeAndCompeIdAndUserId("1",
				1, 1L)).thenReturn(new ArrayList<>());
		fundCompetitionController.getFundCompeUserPosLogByCodeAndCompeIdAndUserId(params);
	}

	@Test
	public void getFundCompeUserPosByCodeAndCompeId() {
		Map<String, Object> params = new HashMap<>();
		params.put("competitionId", 1);
		params.put("fundCode", "1");
		Mockito.when(fundCompetitionService.getFundCompeUserPosByCodeAndCompeIdAndUserId("1",
				1, 1L)).thenReturn(null);
		fundCompetitionController.getFundCompeUserPosByCodeAndCompeId(params);
	}

	@Test
	public void getCompeHold() {
		Mockito.when(fundCompeUserService.getFundCompeUserByCompetitionIdAndUserId(1,
				1L)).thenReturn(null);
		fundCompetitionController.getCompeHold(1);

		FundCompeUser fundCompeUser = new FundCompeUser();
		fundCompeUser.setSurplusMoney(BigDecimal.ONE);
		fundCompeUser.setTotalAsset(BigDecimal.ONE);
		fundCompeUser.setTotalChange(BigDecimal.ONE);
		fundCompeUser.setIsEnd(false);

		FundCompeUserPosLog fundCompeUserPosLog = new FundCompeUserPosLog();
		fundCompeUserPosLog.setFundCode("1");
		fundCompeUserPosLog.setDate(LocalDate.now());
		fundCompeUserPosLog.setAmount(BigDecimal.ONE);
		List<FundCompeUserPosLog> fundCompeUserPosLogList = new ArrayList<>();
		fundCompeUserPosLogList.add(fundCompeUserPosLog);
		fundCompeUser.setFundCompeUserPosLogList(fundCompeUserPosLogList);

		FundCompeUserPos fundCompeUserPos = new FundCompeUserPos();
		fundCompeUserPos.setFundCode("1");
		fundCompeUserPos.setAmount(BigDecimal.ONE);
		List<FundCompeUserPos> fundCompeUserPosList = new ArrayList<>();
		fundCompeUserPosList.add(fundCompeUserPos);
		fundCompeUser.setFundCompeUserPosList(fundCompeUserPosList);

		FundAssembly fundAssembly = new FundAssembly("1");
		fundAssembly.setNAV(1D);
		fundAssembly.setLastOneDayRate(1D);

		Mockito.when(fundCompeUserService.getFundCompeUserByCompetitionIdAndUserId(1,
				1L)).thenReturn(fundCompeUser);
		Mockito.when(fundAssemblyCache.getFundAssembly("1")).thenReturn(null);
		fundCompetitionController.getCompeHold(1);
		Mockito.when(fundAssemblyCache.getFundAssembly("1")).thenReturn(fundAssembly);
		fundCompetitionController.getCompeHold(1);
	}

	@Test
	public void dailyCompeCalculation() {
		List<Integer> list = new ArrayList<>();
		Mockito.when(fundCompeUserService.updateActiveCompetition()).thenReturn(list);
		fundCompetitionController.dailyCompeCalculation();
		list.add(1);
		Mockito.when(fundCompeUserService.updateActiveCompetition()).thenReturn(list);
		fundCompetitionController.dailyCompeCalculation();
	}

	@Test
	public void getRankByCompeId() {
		List<FundCompeRank> list = new ArrayList<>();
		Mockito.when(fundCompeUserService.getTopRankByCompetitionId(1, 5)).thenReturn(list);
		fundCompetitionController.getRankByCompeId(1);
		list.add(new FundCompeRank());
		Mockito.when(fundCompeUserService.getTopRankByCompetitionId(1, 5)).thenReturn(list);
		fundCompetitionController.getRankByCompeId(1);
	}

	@Test
	public void getCompetitionSimpleByCompeId() {
		FundCompetition fundCompetition = new FundCompetition();
		fundCompetition.setCompetitionName("1");
		fundCompetition.setStartDate(LocalDate.now());
		fundCompetition.setEndDate(LocalDate.now());
		Mockito.when(fundCompetitionService.getFundCompetitionByCompeId(1)).thenReturn(null);
		fundCompetitionController.getCompetitionSimpleByCompeId(1);
		Mockito.when(fundCompetitionService.getFundCompetitionByCompeId(1)).thenReturn(fundCompetition);
		fundCompetitionController.getCompetitionSimpleByCompeId(1);
	}

	@Test
	public void getCompetitionDetailByCompeId() {
		FundCompetition fundCompetition = new FundCompetition();
		fundCompetition.setCompetitionName("1");
		fundCompetition.setStartDate(LocalDate.now());
		fundCompetition.setEndDate(LocalDate.now());
		fundCompetition.setCompetitionDescription("1");
		fundCompetition.setCapacity(BigInteger.ONE);
		fundCompetition.setNumber(BigInteger.ONE);
		fundCompetition.setAllowedFundType(2);
		Mockito.when(fundCompetitionService.getFundCompetitionByCompeId(1)).thenReturn(null);
		fundCompetitionController.getCompetitionDetailByCompeId(1);
		Mockito.when(fundCompetitionService.getFundCompetitionByCompeId(1)).thenReturn(fundCompetition);
		fundCompetitionController.getCompetitionDetailByCompeId(1);
	}

	@Test
	public void getAllActivePublicCompetition() {
		FundCompetition fundCompetition = new FundCompetition();
		fundCompetition.setCompetitionName("1");
		fundCompetition.setStartDate(LocalDate.now());
		fundCompetition.setEndDate(LocalDate.now());
		fundCompetition.setCompetitionDescription("1");
		fundCompetition.setCapacity(BigInteger.ONE);
		fundCompetition.setNumber(BigInteger.ONE);
		fundCompetition.setAllowedFundType(2);

		List<FundCompetition> fundCompetitionList = new ArrayList<>();

		Mockito.when(fundCompetitionService.getAllActivePublicCompetition(LocalDate.now()))
				.thenReturn(fundCompetitionList);
		fundCompetitionController.getAllActivePublicCompetition();
		fundCompetitionList.add(fundCompetition);
		Mockito.when(fundCompetitionService.getAllActivePublicCompetition(LocalDate.now()))
				.thenReturn(fundCompetitionList);
		fundCompetitionController.getAllActivePublicCompetition();
	}

	@Test
	public void getAllActivePublicCompetitionByUserId() {
		FundCompetition fundCompetition = new FundCompetition();
		fundCompetition.setCompetitionName("1");
		fundCompetition.setStartDate(LocalDate.now());
		fundCompetition.setEndDate(LocalDate.now());
		fundCompetition.setCompetitionDescription("1");
		fundCompetition.setCapacity(BigInteger.ONE);
		fundCompetition.setNumber(BigInteger.ONE);
		fundCompetition.setAllowedFundType(2);

		List<FundCompetition> fundCompetitionList = new ArrayList<>();

		Mockito.when(fundCompetitionService.getActivePublicCompetitionByUserId(1L, LocalDate.now()))
				.thenReturn(fundCompetitionList);
		fundCompetitionController.getAllActivePublicCompetitionByUserId();
		fundCompetitionList.add(fundCompetition);
		Mockito.when(fundCompetitionService.getActivePublicCompetitionByUserId(1L, LocalDate.now()))
				.thenReturn(fundCompetitionList);
		fundCompetitionController.getAllActivePublicCompetitionByUserId();
	}

	@Test
	public void joinCompetitionByCompeId() {
		Map<String, Object> params = new HashMap<>();
		params.put("competitionId", 1);
		FundCompeUser fundCompeUser = new FundCompeUser(1, 1L, null, null, null, false);
		Mockito.when(fundCompeUserService.joinCompetition(fundCompeUser)).thenReturn(0);
		fundCompetitionController.joinCompetitionByCompeId(params);
		Mockito.when(fundCompeUserService.joinCompetition(fundCompeUser)).thenReturn(-1);
		fundCompetitionController.joinCompetitionByCompeId(params);
		Mockito.when(fundCompeUserService.joinCompetition(fundCompeUser)).thenReturn(-2);
		fundCompetitionController.joinCompetitionByCompeId(params);
		Mockito.when(fundCompeUserService.joinCompetition(fundCompeUser)).thenReturn(-3);
		fundCompetitionController.joinCompetitionByCompeId(params);
	}
}
