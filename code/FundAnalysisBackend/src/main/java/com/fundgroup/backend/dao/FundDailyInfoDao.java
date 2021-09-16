package com.fundgroup.backend.dao;

import com.fundgroup.backend.dto.FundDailySimp;
import com.fundgroup.backend.entity.FundDailyInfo;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface FundDailyInfoDao {
    FundDailyInfo getFundDailyInfoByCodeDate(String fundCode, LocalDate date);

    List<FundDailySimp> getFundDailyRangeSimp(String fundCode, LocalDate beginDate, LocalDate endDate);

    List<FundDailyInfo> getFundDailyRange(String fundCode, LocalDate beginDate, LocalDate endDate);

    List<Double> getNewestNAVs(String fundCode, Integer dayNum);

    List<FundDailyInfo> getFundDailyPage(String fundCode, Integer page, Integer size);

    List<FundDailySimp> getFundDailyDetailFourByCode(String fundCode, Integer page, Integer size);

    FundDailyInfo getFurthestRecord(String fundCode);

    FundDailyInfo saveFundDailyInfo(FundDailyInfo fundDailyInfo);
    
    Integer getDailyInfoCountByUpdateDate(LocalDate updateDate);

}
