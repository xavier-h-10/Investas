package com.fundgroup.backend.repository;

import com.fundgroup.backend.entity.FundCompeUserPos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FundCompeUserPosRepository extends JpaRepository<FundCompeUserPos, Long> {

	@Query("select c " +
			"from FundCompeUserPos c " +
			"where c.fundCode = :fundCode and c.fundCompeUser.userId = :userId " +
			"and c.fundCompeUser.competitionId = :competitionId")
	FundCompeUserPos getFundCompeUserPosByCodeAndCompeIdAndUserId(@Param("fundCode") String fundCode,
																  @Param("competitionId") Integer competitionId,
																  @Param("userId") Long userId);


}
