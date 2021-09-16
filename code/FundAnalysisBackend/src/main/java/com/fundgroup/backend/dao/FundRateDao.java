package com.fundgroup.backend.dao;

import com.fundgroup.backend.dto.FundRankingByRecentRate;
import com.fundgroup.backend.dto.FundRateRank;
import com.fundgroup.backend.entity.FundRate;
import com.fundgroup.backend.dto.FundRateRecent;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface FundRateDao {
    void updateFundRate(List<FundRate> fundRateList);
    void updateFundRate(FundRate fundRate);
    FundRateRecent getFundRecRate(String fundCode);
    FundRate getFundRateByCode(String fundCode);

    List<FundRankingByRecentRate> getTopFundRateByMonth();

    List<FundRankingByRecentRate> getTopFundRateByYear();

    List<FundRateRank> getAllFundRateRank();

    Integer getDailyInfoCountByUpdateDate(LocalDate date);
}
