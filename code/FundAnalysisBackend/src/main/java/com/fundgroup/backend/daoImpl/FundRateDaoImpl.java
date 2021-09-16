package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dao.FundRateDao;
import com.fundgroup.backend.dto.FundRankingByRecentRate;
import com.fundgroup.backend.dto.FundRateRank;
import com.fundgroup.backend.entity.FundRate;
import com.fundgroup.backend.dto.FundRateRecent;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import com.fundgroup.backend.repository.FundRateRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FundRateDaoImpl implements FundRateDao {
    @Autowired
    private FundRateRepository fundRateRepository;

    @Autowired
    private FundBasicInfoRepository fundBasicInfoRepository;

    @Override
    public void updateFundRate(List<FundRate> fundRateList)
    {
        fundRateRepository.saveAll(fundRateList);
    }

    @Override
    public void updateFundRate(FundRate fundRate)
    {
        fundRateRepository.save(fundRate);
    }

    @Override
    public FundRateRecent getFundRecRate(String fundCode)
    {
        return fundRateRepository.getRateInOneYear(fundCode);
    }

    @Override
    public FundRate getFundRateByCode(String fundCode)
    {
        return fundRateRepository.getFundRateByFundCode(fundCode);
    }

    @Override
    public List<FundRankingByRecentRate> getTopFundRateByMonth() {
        List<FundRate> fundRates = fundRateRepository.getTopFundRateByMonth();
        return fundRatesToFundRankings(fundRates);
    }

    @Override
    public List<FundRankingByRecentRate> getTopFundRateByYear() {
        List<FundRate> fundRates = fundRateRepository.getTopFundRateByYear();
        return fundRatesToFundRankings(fundRates);
    }

    private List<FundRankingByRecentRate> fundRatesToFundRankings(List<FundRate> fundRates){
        List<FundRankingByRecentRate> fundRankingByRecentRates = new ArrayList<>();
        for(FundRate fundRate: fundRates){
            FundRankingByRecentRate fundRankingByRecentRate = new FundRankingByRecentRate(fundRate);
            fundRankingByRecentRate.setFundName(fundBasicInfoRepository.getFundNameByFundCode(fundRankingByRecentRate.getFundCode()));

            fundRankingByRecentRates.add(fundRankingByRecentRate);
        }
        return fundRankingByRecentRates;
    }

    @Override
    public List<FundRateRank> getAllFundRateRank()
    {
        List<FundRateRank> fundRateRanks=new ArrayList<>();
        List<Object[]> objects = fundRateRepository.getAllFundRateRank();
        for(Object[] objectList:objects)
        {
            if(objectList==null)
                continue;
            String fundCode=objectList[0].toString();
            Integer lastOneDay= Integer.valueOf(objectList[1].toString());
            Integer lastOneWeek= Integer.valueOf(objectList[2].toString());
            Integer lastOneMonth= Integer.valueOf(objectList[3].toString());
            Integer lastThreeMonths= Integer.valueOf(objectList[4].toString());
            Integer lastSixMonths= Integer.valueOf(objectList[5].toString());
            Integer lastOneYear= Integer.valueOf(objectList[6].toString());
            Integer lastTwoYears= Integer.valueOf(objectList[7].toString());
            Integer lastThreeYears= Integer.valueOf(objectList[8].toString());
            Integer lastFiveYears= Integer.valueOf(objectList[9].toString());
            Integer fromBeginning= Integer.valueOf(objectList[10].toString());
            Integer fromThisYear= Integer.valueOf(objectList[11].toString());

            FundRateRank fundRateRank=new FundRateRank(fundCode,lastOneDay,lastOneWeek,lastOneMonth,lastThreeMonths,
                    lastSixMonths,lastOneYear,lastTwoYears,lastThreeYears,lastFiveYears,fromBeginning,fromThisYear);
            fundRateRanks.add(fundRateRank);
        }
        return fundRateRanks;
    }

    @Override
    public Integer getDailyInfoCountByUpdateDate(LocalDate date)
    {
        return fundRateRepository.getDailyInfoCountByUpdateDate(date);
    }
}
