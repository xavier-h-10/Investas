package com.fundgroup.backend.repository;

import com.fundgroup.backend.dto.ActiveCompetitionUser;
import com.fundgroup.backend.entity.FundCompetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FundCompetitionRepository extends JpaRepository<FundCompetition, Integer> {
	@Query("select c from FundCompetition c where c.endDate>=:date and c.startDate<=:date and c.isPublic=true")
	List<FundCompetition> getActivePublicCompetition(@Param("date") LocalDate date);

	@Query("select c from FundCompetition c where c.endDate>=:date and c.startDate<=:date")
	List<FundCompetition> getActiveCompetition(@Param("date") LocalDate date);

	@Query("select new com.fundgroup.backend.dto.ActiveCompetitionUser(c.competitionId, c.competitionName, c.endDate, " +
			"c.capacity, c.number, cu.surplusMoney, c.creatorId, c.competitionDescription) " +
			"from FundCompetition c, FundCompeUser cu " +
			"where c.endDate >= :date and c.competitionId = cu.competitionId and cu.userId = :userId " +
			"and mod(c.allowedFundType, 2 * :fundType) >= :fundType and c.isPublic = true and cu.isEnd = false")
	List<ActiveCompetitionUser> getActiveCompetitionByUserIdAndFundTypeAndEndDate(@Param("userId") Long userId,
																				  @Param("fundType") Integer fundType,
																				  @Param("date") LocalDate date);

	@Query("select c from FundCompetition c, FundCompeUser cu where cu.competitionId=c.competitionId and c.endDate >= :date and cu.userId = :userId and c.isPublic = true and cu.isEnd = false")
	List<FundCompetition> getActivePublicCompetitionByUserId(@Param("userId") Long userId, @Param("date") LocalDate date);

	@Query("select c.isPublic, count(c.competitionId) from FundCompetition c group by c.isPublic")
  	Object getMonitorData();

	@Query("select c from FundCompetition  c where c.competitionId=:competitionId")
	FundCompetition getFundCompetitionByCompeId(@Param("competitionId") Integer competitionId);

	@Query("select c from FundCompetition  c where c.competitionId=:competitionId and c.endDate>=:date and c.startDate<=:date and c.isPublic=true")
	FundCompetition getActivePublicCompetitionByCompeId(@Param("competitionId") Integer competitionId,@Param("date") LocalDate date);
}
