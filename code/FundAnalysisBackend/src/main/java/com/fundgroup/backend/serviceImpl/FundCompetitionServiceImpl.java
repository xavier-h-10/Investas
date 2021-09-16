package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.dao.FundCompetitionDao;
import com.fundgroup.backend.dto.ActiveCompetitionUser;
import com.fundgroup.backend.entity.*;
import com.fundgroup.backend.repository.*;
import com.fundgroup.backend.service.FundCompetitionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class FundCompetitionServiceImpl implements FundCompetitionService {

	private final Logger logger = LoggerFactory.getLogger(FundCompetitionServiceImpl.class);

	@Autowired
	FundCompetitionDao fundCompetitionDao;
	@Autowired
	FundCompetitionRepository fundCompetitionRepository;
	@Autowired
	FundBasicInfoRepository fundBasicInfoRepository;
	@Autowired
	FundCompeUserRepository fundCompeUserRepository;
	@Autowired
	FundCompeUserPosRepository fundCompeUserPosRepository;
	@Autowired
	FundCompeUserPosLogRepository fundCompeUserPosLogRepository;
	@Autowired
	FundDailyInfoRepository fundDailyInfoRepository;

//	@Autowired
//	void setFundCompetitionDao(FundCompetitionDao fundCompetitionDao) {
//		this.fundCompetitionDao = fundCompetitionDao;
//	}

	@Override
	public Integer createCompetition(Map<String, Object> value) {
		return fundCompetitionDao.createCompetition(value);
	}

	@Override
	public List<FundCompetition> getAll() {
		return fundCompetitionDao.getAll();
	}

	@Override
	public List<ActiveCompetitionUser> getActiveCompetitionByUserIdAndFundTypeAndEndDate(Long userId, Integer fundType,
																						 LocalDate date) {
		return fundCompetitionRepository.getActiveCompetitionByUserIdAndFundTypeAndEndDate(userId, fundType, date);
	}

	@Override
	public List<FundCompetition> removeCompetition(Integer competitionId) {
		return fundCompetitionDao.removeById(competitionId);
	}

	@Override
	public String buyFund(Integer competitionId, Long userId, String fundCode, BigDecimal amount) {
		FundBasicInfo fundBasicInfo = fundBasicInfoRepository.getById(fundCode);
		FundCompetition fundCompetition = fundCompetitionRepository.getById(competitionId);
		FundCompeUser fundCompeUser =
				fundCompeUserRepository.getFundCompeUserByCompetitionIdAndUserId(competitionId, userId);
		FundCompeUserPos fundCompeUserPos =
				fundCompeUserPosRepository.getFundCompeUserPosByCodeAndCompeIdAndUserId(fundCode, competitionId,
						userId);

		try {
			if (fundCompetition.getEndDate().isBefore(LocalDate.now())) {
				return "比赛已结束。";
			}
		} catch (EntityNotFoundException ex) {
			logger.info("Unable to find " + FundCompetition.class + " with competitionId "
					+ competitionId.toString());
			return "该比赛不存在";
		}
		if (!fundCompetition.getIsPublic()) {
			return "比赛未公开。";
		}
		try {
			if (!fundCompetition.allow(fundBasicInfo.getFundType())) {
				System.out.println(fundBasicInfo.getFundType());
				return "该比赛不允许此类基金参与投资。";
			}
		} catch (EntityNotFoundException ex) {
			logger.info("Unable to find " + FundBasicInfo.class + " with id " + fundCode);
			return "该基金不存在";
		}
		if (fundCompeUser == null) {
			return "用户未参赛。";
		}
		if (fundCompeUserPos != null) {
			if (fundCompeUserPos.getAmount().add(amount).compareTo(BigDecimal.valueOf(0)) < 0) {
				return "卖出的资金超过投资的资金。";
			}
		} else {
			if (amount.compareTo(BigDecimal.valueOf(0)) < 0) {
				return "卖出的资金超过投资的资金。";
			}
		}
		if (amount.compareTo(fundCompeUser.getSurplusMoney()) > 0) {
			return "剩余的资金已经不足够进行相应数量的投资。";
		}

		FundCompeUserPosLog fundCompeUserPosLog = new FundCompeUserPosLog();
		fundCompeUserPosLog.setFundCompeUser(fundCompeUser);
		fundCompeUserPosLog.setFundCode(fundCode);
		fundCompeUserPosLog.setAmount(amount);
		fundCompeUserPosLog.setDate(LocalDate.now());

		if (fundCompeUserPos == null) {
			fundCompeUserPos = new FundCompeUserPos();
			fundCompeUserPos.setFundCode(fundCode);
			fundCompeUserPos.setFundCompeUser(fundCompeUser);
			fundCompeUserPos.setAmount(amount);
			List<FundCompeUserPos> fundCompeUserPosList = fundCompeUser.getFundCompeUserPosList();
			fundCompeUserPosList.add(fundCompeUserPos);
			fundCompeUser.setFundCompeUserPosList(fundCompeUserPosList);

			fundCompeUser.setSurplusMoney(fundCompeUser.getSurplusMoney().subtract(amount));
		} else {
			fundCompeUser.getFundCompeUserPosList().remove(fundCompeUserPos);
			fundCompeUserPos.setFundCode(fundCode);
			fundCompeUserPos.setFundCompeUser(fundCompeUser);
			fundCompeUserPos.setAmount(fundCompeUserPos.getAmount().add(amount));
			fundCompeUser.setSurplusMoney(fundCompeUser.getSurplusMoney().subtract(amount));
			if (fundCompeUserPos.getAmount().compareTo(BigDecimal.ZERO) != 0) {
				List<FundCompeUserPos> fundCompeUserPosList = fundCompeUser.getFundCompeUserPosList();
				fundCompeUserPosList.add(fundCompeUserPos);
				fundCompeUser.setFundCompeUserPosList(fundCompeUserPosList);
			} else {
				fundCompeUserPosRepository.delete(fundCompeUserPos);
			}
		}

		fundCompeUserPosLogRepository.saveAndFlush(fundCompeUserPosLog);
		fundCompeUserRepository.saveAndFlush(fundCompeUser);
		return null;
	}

	@Override
	public List<FundCompeUserPosLog> getFundCompeUserPosLogByCodeAndCompeIdAndUserId(String fundCode, Integer compeId,
																					 Long userId) {
		return fundCompeUserPosLogRepository.getFundCompeUserPosLogByCodeAndCompeIdAndUserId(fundCode, compeId, userId);
	}

	@Override
	public FundCompeUserPos getFundCompeUserPosByCodeAndCompeIdAndUserId(String fundCode, Integer competitionId,
																		 Long userId) {
		return fundCompeUserPosRepository.getFundCompeUserPosByCodeAndCompeIdAndUserId(fundCode, competitionId, userId);
	}

	@Override
	public FundCompetition getFundCompetitionByCompeId(Integer competitionId) {
		return fundCompetitionRepository.getFundCompetitionByCompeId(competitionId);
	}

	@Override
	public List<FundCompetition> getAllActivePublicCompetition(LocalDate date) {
		return fundCompetitionRepository.getActivePublicCompetition(date);
	}

	@Override
	public List<FundCompetition> getActivePublicCompetitionByUserId(Long userId, LocalDate date) {
		return fundCompetitionRepository.getActivePublicCompetitionByUserId(userId, date);
	}

}
