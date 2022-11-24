package com.fundgroup.backend.repository;

import com.fundgroup.backend.entity.FundCompeUserPos;
import com.fundgroup.backend.entity.FundCompeUserPosLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundCompeUserPosLogRepository extends JpaRepository<FundCompeUserPosLog, Long> {

	@Query("select c " +
			"from FundCompeUserPosLog c " +
			"where c.fundCode = :fundCode and c.fundCompeUser.userId = :userId " +
			"and c.fundCompeUser.competitionId = :compeId")
	List<FundCompeUserPosLog> getFundCompeUserPosLogByCodeAndCompeIdAndUserId(@Param("fundCode") String fundCode,
																			  @Param("compeId") Integer compeId,
																			  @Param("userId") Long userId);
}
