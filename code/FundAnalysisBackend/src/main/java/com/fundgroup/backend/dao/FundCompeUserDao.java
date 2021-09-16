package com.fundgroup.backend.dao;

import com.fundgroup.backend.entity.FundCompeUser;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundCompeUserDao {
    List<FundCompeUser> getFundCompeUsersByCompetitionId(Integer competitionId);
    FundCompeUser getFundCompeUserByCompetitionIdAndUserId(Integer competitionId,Long userId);
    void updateFundCompeUserList(List<FundCompeUser> fundCompeUserList);
    List<FundCompeUser> getAllByUserId(Long userId);
    List<FundCompeUser> getAllActiveByUserId(Long userId);
    List<Object[]> getTopRankByCompetitionId(Integer competitionId, Integer topNumber);
    FundCompeUser saveFundCompeUser(FundCompeUser fundCompeUser);
}
