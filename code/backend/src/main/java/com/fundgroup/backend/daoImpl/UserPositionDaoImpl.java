package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.UserPositionDao;
import com.fundgroup.backend.entity.UserPosition;
import com.fundgroup.backend.repository.UserPositionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserPositionDaoImpl implements UserPositionDao {

	@Autowired
	UserPositionRepository userPositionRepository;

//	@Autowired
//	void setUserPositionRepository(UserPositionRepository userPositionRepository) {
//		this.userPositionRepository = userPositionRepository;
//	}

	@Override
	public void save(Long userId, String fundCode, LocalDate beginDate, Double startPrice, Double amount) {
		UserPosition userPosition = userPositionRepository.getUserPositionByUserIdAndFundCode(userId, fundCode);
		if (userPosition == null) {
			userPosition = new UserPosition();
		}
		userPosition.setUserId(userId);
		userPosition.setAmount(BigDecimal.valueOf(amount));
		userPosition.setHoldingStartDate(beginDate);
		userPosition.setFundCode(fundCode);
		userPosition.setStartPrice(startPrice);
		userPosition.setHoldingEndDate(LocalDate.parse("9999-12-31"));
		userPositionRepository.save(userPosition);
	}

	@Override
	public List<UserPosition> getPositions(LocalDate now, Long userId) {
		return userPositionRepository.findAllByUserIdAndHoldingEndDate(userId, now);
	}

	@Override
	public UserPosition getUserPositionByUserIdAndFundCode(Long userId, String fundCode) {
		return userPositionRepository.getUserPositionByUserIdAndFundCode(userId, fundCode);
	}

	@Override
	public int delUserPositionByUserIdAndFundCode(Long userId, String fundCode) {
		UserPosition userPosition = userPositionRepository.getUserPositionByUserIdAndFundCode(userId, fundCode);
		if (userPosition == null) {
			return -1;
		} else {
			userPositionRepository.delete(userPosition);
			return 0;
		}
	}
}
