package com.fundgroup.backend.service;

import com.fundgroup.backend.dto.ActiveCompetitionUser;
import com.fundgroup.backend.entity.FundCompeUserPos;
import com.fundgroup.backend.entity.FundCompeUserPosLog;
import com.fundgroup.backend.entity.FundCompetition;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FundCompetitionService {

	Integer createCompetition(Map<String, Object> value);

	List<FundCompetition> getAll();

	List<FundCompetition> removeCompetition(Integer competitionId);

	List<ActiveCompetitionUser> getActiveCompetitionByUserIdAndFundTypeAndEndDate(Long userId, Integer fundType,
																				  LocalDate date);

	String buyFund(Integer competitionId, Long userId, String fundCode, BigDecimal amount);

//  String modifyFund(Integer competitionId, Long userId, String fundCode, BigDecimal amount);

	List<FundCompeUserPosLog> getFundCompeUserPosLogByCodeAndCompeIdAndUserId(String fundCode, Integer compeId,
																			  Long userId);

	List<FundCompetition> getActivePublicCompetitionByUserId(Long userId,LocalDate date);

	FundCompeUserPos getFundCompeUserPosByCodeAndCompeIdAndUserId(String fundCode, Integer competitionId,  Long userId);

	FundCompetition getFundCompetitionByCompeId(Integer competitionId);

	List<FundCompetition> getAllActivePublicCompetition(LocalDate date);

}
