package com.fundgroup.backend.repository;

import com.fundgroup.backend.dto.PositionInfoFund;
import com.fundgroup.backend.entity.UserPosition;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserPositionRepository extends JpaRepository<UserPosition, BigInteger> {

	List<UserPosition> findAllByUserIdAndHoldingEndDate(Long userId, LocalDate end);

	@Query("select new com.fundgroup.backend.dto.PositionInfoFund(u.fundCode) " +
			"from UserPosition u " +
			"where u.userId = :userId ")
	List<PositionInfoFund> getFundByUserId(@Param("userId") Long userId);

	UserPosition getUserPositionByUserIdAndFundCode(Long userId, String fundCode);
}
