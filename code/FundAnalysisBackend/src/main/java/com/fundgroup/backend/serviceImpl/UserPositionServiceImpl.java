package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.dao.UserPositionDao;
import com.fundgroup.backend.entity.User;
import com.fundgroup.backend.entity.UserPosition;
import com.fundgroup.backend.service.UserPositionService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPositionServiceImpl implements UserPositionService {

	@Autowired
	UserPositionDao userPositionDao;
	@Autowired
	FundDailyInfoDao fundDailyInfoDao;

//	@Autowired
//	void setUserPositionDao(UserPositionDao userPositionDao) {
//		this.userPositionDao = userPositionDao;
//	}
//
//	@Autowired
//	void setFundDailyInfoDao(FundDailyInfoDao fundDailyInfoDao) {
//		this.fundDailyInfoDao = fundDailyInfoDao;
//	}

	@Override
	public Integer setPositionByMoney(Long userId, String fundCode, Double sumAmount) {
		List<Double> currNAV = fundDailyInfoDao.getNewestNAVs(fundCode, 1);
		if (currNAV == null) {
			return -1;
		}
		Double nav = currNAV.get(0);
		Double amount = sumAmount / nav;
		LocalDate beginDate = LocalDate.now();
		userPositionDao.save(userId, fundCode, beginDate, nav, amount);
		return 0;
	}

	@Override
	public int setPositionByAmount(Long userId, String fundCode, Double amount) {
		List<Double> currNAV = fundDailyInfoDao.getNewestNAVs(fundCode, 1);
		if (currNAV == null) {
			return -1;
		}
		Double nav = currNAV.get(0);
		System.out.println(LocalDate.now());
		userPositionDao.save(userId, fundCode, LocalDate.now(), nav, amount);
		return 0;
	}

	@Override
	public List<UserPosition> getPositions(Long userId) {
		LocalDate localDate = LocalDate.now();
		return userPositionDao.getPositions(localDate, userId);
	}

	@Override
	public UserPosition getUserPositionByUserIdAndFundCode(Long userId, String fundCode) {
		return userPositionDao.getUserPositionByUserIdAndFundCode(userId, fundCode);
	}

	@Override
	public int delUserPositionByUserIdAndFundCode(Long userId, String fundCode) {
		return userPositionDao.delUserPositionByUserIdAndFundCode(userId, fundCode);
	}
}
