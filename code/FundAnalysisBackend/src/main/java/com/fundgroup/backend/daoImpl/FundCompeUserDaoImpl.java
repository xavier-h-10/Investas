package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.FundCompeUserDao;
import com.fundgroup.backend.entity.FundCompeUser;
import com.fundgroup.backend.repository.FundCompeUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FundCompeUserDaoImpl implements FundCompeUserDao {
    @Autowired
    private FundCompeUserRepository fundCompeUserRepository;

    @Override
    public List<FundCompeUser> getFundCompeUsersByCompetitionId(Integer competitionId)
    {
        return fundCompeUserRepository.getFundCompeUsersByCompetitionId(competitionId);
    }

    @Override
    public void updateFundCompeUserList(List<FundCompeUser> fundCompeUserList)
    {
        fundCompeUserRepository.saveAll(fundCompeUserList);
    }

    @Override
    public List<FundCompeUser> getAllByUserId(Long userId)
    {
        return fundCompeUserRepository.getAllByUserId(userId);
    }

    @Override
    public List<FundCompeUser> getAllActiveByUserId(Long userId)
    {
        return fundCompeUserRepository.getAllActiveByUserId(userId);
    }

    @Override
    public FundCompeUser getFundCompeUserByCompetitionIdAndUserId(Integer competitionId,Long userId)
    {
        return fundCompeUserRepository.getFundCompeUserByCompetitionIdAndUserId(competitionId,userId);
    }

    @Override
    public List<Object[]> getTopRankByCompetitionId(Integer competitionId, Integer topNumber)
    {
        return fundCompeUserRepository.getTopRankByCompetitionId(competitionId,topNumber);
    }

    @Override
    public FundCompeUser saveFundCompeUser(FundCompeUser fundCompeUser)
    {
        return fundCompeUserRepository.save(fundCompeUser);
    }

}
