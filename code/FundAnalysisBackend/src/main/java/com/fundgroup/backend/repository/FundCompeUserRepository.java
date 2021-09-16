package com.fundgroup.backend.repository;

import com.fundgroup.backend.entity.FundCompeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FundCompeUserRepository extends JpaRepository<FundCompeUser, Long> {
    @Query("select f from FundCompeUser f where f.competitionId=:competitionId")
    List<FundCompeUser> getFundCompeUsersByCompetitionId(@Param("competitionId") Integer competitionId);

    @Query("select f from FundCompeUser f where f.competitionId=:competitionId and f.userId=:userId")
    FundCompeUser getFundCompeUserByCompetitionIdAndUserId(@Param("competitionId") Integer competitionId,@Param("userId") Long userId);

    @Query("select f from FundCompeUser f where f.userId=:userId")
    List<FundCompeUser> getAllByUserId(@Param("userId") Long userId);

    @Query("select f from FundCompeUser f where f.userId=:userId and f.isEnd=false")
    List<FundCompeUser> getAllActiveByUserId(@Param("userId") Long userId);

    @Query(value="select user_id,cast((total_asset-total_change) as decimal)/total_change as rate from fund_competition_user where competition_id=:competitionId order by rate desc limit :topNumber",nativeQuery = true)
    List<Object[]> getTopRankByCompetitionId(@Param("competitionId") Integer competitionId, @Param("topNumber") Integer topNumber);
}
