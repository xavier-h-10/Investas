package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.dao.*;
import com.fundgroup.backend.dto.FundCompeRank;
import com.fundgroup.backend.entity.*;
import com.fundgroup.backend.repository.FundCompeUserPosRepository;
import com.fundgroup.backend.service.FundCompeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FundCompeUserServiceImpl implements FundCompeUserService {

    @Autowired
    private FundCompeUserDao fundCompeUserDao;

    @Autowired
    private FundDailyInfoDao fundDailyInfoDao;

    @Autowired
    private FundCompetitionDao fundCompetitionDao;

    @Autowired
    private UserDao userDao;


    private BigDecimal fundAssertCalculate(List<FundCompeUserPos> fundCompeUserPosList)
    {
        LocalDate today=LocalDate.now();
        BigDecimal result = BigDecimal.valueOf(0);
        for(FundCompeUserPos fundCompeUserPos:fundCompeUserPosList)
        {
            String fundCode=fundCompeUserPos.getFundCode();
            BigDecimal amount=fundCompeUserPos.getAmount();
            FundDailyInfo fundDailyInfo=fundDailyInfoDao.getFundDailyInfoByCodeDate(fundCode,today);
            if(fundDailyInfo==null)
            {
                return BigDecimal.valueOf(-1);
            }
            fundCompeUserPos.setCalculateDate(today);
            BigDecimal nav=BigDecimal.valueOf(fundDailyInfo.getNAV());
            BigDecimal tmpMult = nav.multiply(amount);
            result = result.add(tmpMult);
        }
        return result;
    }

    private boolean updateCompeUserByCompeId(Integer competitionId)
    {
        List<FundCompeUser> fundCompeUsers=fundCompeUserDao.getFundCompeUsersByCompetitionId(competitionId);
        if(fundCompeUsers.isEmpty())
            return false;

        for(FundCompeUser fundCompeUser:fundCompeUsers)
        {
            List<FundCompeUserPos> activeItems=fundCompeUser.getFundCompeUserPosList();
            BigDecimal fundAssert=fundAssertCalculate(activeItems);
            if(fundAssert.compareTo(BigDecimal.valueOf(0))<0)
                return false;
            fundCompeUser.setTotalAsset(fundAssert);
            fundCompeUser.setFundCompeUserPosList(activeItems);
        }
        fundCompeUserDao.updateFundCompeUserList(fundCompeUsers);

        return true;
    }

    /**
     * updateActiveCompetition
     * @return unsuccessfully update competition id
     */
    @Override
    public List<Integer> updateActiveCompetition()
    {
        List<Integer> failCompetition=new ArrayList<>();
        LocalDate today=LocalDate.now();
        List<FundCompetition> fundCompetitionList=fundCompetitionDao.getActiveCompetition(today);
        for(FundCompetition fundCompetition:fundCompetitionList)
        {
            Integer competitionId=fundCompetition.getCompetitionId();
            if(!updateCompeUserByCompeId(competitionId))
            {
                failCompetition.add(competitionId);
            }
        }
        return failCompetition;
    }

    @Override
    public List<FundCompeUser> getAll(Long userId)
    {
        return fundCompeUserDao.getAllByUserId(userId);
    }

    @Override
    public List<FundCompeUser> getActive(Long userId)
    {
        return fundCompeUserDao.getAllActiveByUserId(userId);
    }

    @Override
    public FundCompeUser getFundCompeUserByCompetitionIdAndUserId(Integer competitionId,Long userId)
    {
        return fundCompeUserDao.getFundCompeUserByCompetitionIdAndUserId(competitionId,userId);
    }

    @Override
    public List<FundCompeRank> getTopRankByCompetitionId(Integer competitionId, Integer topNumber)
    {
        List<Object[]> topRankList=fundCompeUserDao.getTopRankByCompetitionId(competitionId,topNumber);
        if(topRankList.isEmpty())
            return Collections.emptyList();
        List<FundCompeRank> fundCompeRankList=new ArrayList<>();
        Integer rank=1;
        for(Object[] topRank : topRankList)
        {
            Long userId=Long.parseLong(String.valueOf(topRank[0]));
            Double rate=Double.parseDouble(String.valueOf(topRank[1]));
            User user=userDao.findUserByUserId(userId);
            String nickname;
            if(user==null)
                nickname="用户已注销";
            else
                nickname=user.getNickname();
            FundCompeRank fundCompeRank=new FundCompeRank(rank,rate,nickname);
            fundCompeRankList.add(fundCompeRank);
            rank++;
        }
        return fundCompeRankList;
    }

    /**
     *
     * @param init
     * @return -1:already join | -2:competition not valid | -3: capacity full | 0:success
     */
    @Override
    public Integer joinCompetition(FundCompeUser init)
    {
        FundCompetition fundCompetition=fundCompetitionDao.getActivePublicCompetitionByCompeId(init.getCompetitionId());
        if(fundCompetition==null)
            return -2;
        if(fundCompetition.getCapacity().compareTo(fundCompetition.getNumber())<=0)
        {
            return -3;
        }
        FundCompeUser fundCompeUser=fundCompeUserDao.getFundCompeUserByCompetitionIdAndUserId(init.getCompetitionId(),init.getUserId());
        if(fundCompeUser!=null)
            return -1;
        BigDecimal capital=new BigDecimal(fundCompetition.getInitialCapital());
        init.setSurplusMoney(capital);
        init.setTotalAsset(capital);
        init.setTotalChange(capital);
        fundCompeUserDao.saveFundCompeUser(init);
        return 0;
    }

}
