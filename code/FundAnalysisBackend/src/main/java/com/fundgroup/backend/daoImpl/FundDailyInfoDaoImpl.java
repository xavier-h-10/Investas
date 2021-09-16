package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.dto.FundDailySimp;
import com.fundgroup.backend.entity.FundDailyInfo;
import com.fundgroup.backend.repository.FundDailyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class FundDailyInfoDaoImpl implements FundDailyInfoDao {

    @Autowired
    private FundDailyInfoRepository fundDailyInfoRepository;

    @Override
    public FundDailyInfo getFundDailyInfoByCodeDate(String fundCode, LocalDate date) {
        return fundDailyInfoRepository.getFundDailyInfoByCodeDate(fundCode, date);
    }

    @Override
    public List<FundDailySimp> getFundDailyRangeSimp(String fundCode, LocalDate beginDate, LocalDate endDate) {
        return fundDailyInfoRepository.getFundDailyRangeSimp(fundCode, beginDate, endDate);
    }

    @Override
    public List<FundDailyInfo> getFundDailyPage(String fundCode, Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size);
        return fundDailyInfoRepository.getFundDailyInfoByCode(fundCode, pageable);
    }

    @Override
    public List<FundDailySimp> getFundDailyDetailFourByCode(String fundCode, Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size);
        return fundDailyInfoRepository.getFundDailyDetailFourByCode(fundCode, pageable);
    }

    @Override
    public List<FundDailyInfo> getFundDailyRange(String fundCode, LocalDate beginDate, LocalDate endDate) {
        return fundDailyInfoRepository.getFundDailyRange(fundCode, beginDate, endDate);
    }

    @Override
    public List<Double> getNewestNAVs(String fundCode, Integer dayNum) {
        return fundDailyInfoRepository.getNewestNAVs(fundCode, dayNum);
    }

    @Override
    public FundDailyInfo getFurthestRecord(String fundCode) {
        return fundDailyInfoRepository.findFirstByFundCodeOrderByUpdateDate(fundCode);
    }

    @Override
    public FundDailyInfo saveFundDailyInfo(FundDailyInfo fundDailyInfo) {
        return fundDailyInfoRepository.save(fundDailyInfo);
    }

    @Override
    public Integer getDailyInfoCountByUpdateDate(LocalDate updateDate)
    {
        return fundDailyInfoRepository.getDailyInfoCountByUpdateDate(updateDate);
    }

}
