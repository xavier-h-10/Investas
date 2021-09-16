package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dao.FundEstimateDao;
import com.fundgroup.backend.dto.FundEstimateSimp;
import com.fundgroup.backend.service.FundEstimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class FundEstimateServiceImpl implements FundEstimateService {
    @Autowired
    private FundEstimateDao fundEstimateDao;

    @Override
    public List<FundEstimateSimp> getTodayEstimateByCode(String fundCode)
    {
        LocalDateTime endTime=LocalDateTime.now();
        //9:30开盘，出现数据
        LocalDateTime beginTime= LocalDate.now().atTime(9,30);
        return fundEstimateDao.getFundEstimateRange(fundCode,beginTime,endTime);
    }

    @Override
    public void deleteEstimateRange(TimeType timeType)
    {
        LocalDate today=LocalDate.now();
        LocalDate endDate;
        switch(timeType)
        {
            case ONE_DAY_UNTIL:
                endDate=today.minusDays(1);
                break;
            case ONE_WEEK_UNTIL:
                endDate=today.minusWeeks(1);
                break;
            case ONE_MONTH_UNTIL:
                endDate=today.minusMonths(1);
                break;
            default:
                return;
        }
        LocalDateTime beginDateTime=LocalDateTime.of(1960,1,1,0,0);
        LocalDateTime endDateTime=LocalDateTime.of(endDate, LocalTime.of(0,0));
        fundEstimateDao.deleteRange(beginDateTime,endDateTime);
    }
}
