package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.FundEstimateDao;
import com.fundgroup.backend.dto.FundEstimateSimp;
import com.fundgroup.backend.repository.FundEstimateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FundEstimateDaoImpl implements FundEstimateDao {
    @Autowired
    private FundEstimateRepository fundEstimateRepository;

    @Override
    public List<FundEstimateSimp> getFundEstimateRange(String fundCode, LocalDateTime beginTime, LocalDateTime endTime)
    {
        return fundEstimateRepository.getFundEstimateRange(fundCode,beginTime,endTime);
    }

    @Override
    @Transactional
    public void deleteRange(LocalDateTime beginTime, LocalDateTime endTime)
    {
        fundEstimateRepository.deleteRange(beginTime,endTime);
    }
}
