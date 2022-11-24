package com.fundgroup.backend.service;

import com.fundgroup.backend.dto.FundCompeRank;
import com.fundgroup.backend.entity.FundCompeUser;

import java.util.List;

public interface FundCompeUserService {
    List<Integer> updateActiveCompetition();
    List<FundCompeUser> getAll(Long userId);
    List<FundCompeUser> getActive(Long userId);
    FundCompeUser getFundCompeUserByCompetitionIdAndUserId(Integer competitionId,Long userId);
    List<FundCompeRank> getTopRankByCompetitionId(Integer competitionId, Integer topNumber);
    Integer joinCompetition(FundCompeUser init);
}
