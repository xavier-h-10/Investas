package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.dto.FundDailySimp;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundDailyInfo;
import com.fundgroup.backend.service.FundDailyInfoService;
import com.fundgroup.backend.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FundDailyInfoServiceImpl implements FundDailyInfoService {
    @Autowired
    private FundDailyInfoDao fundDailyInfoDao;

    @Autowired
    private FundBasicInfoDao fundBasicInfoDao;

    @Override
    public List<FundDailySimp> getFundDailyTimeRange(String fundCode, TimeType timeType)
    {
        LocalDate today=LocalDate.now();
        LocalDate beginDate= DateUtils.minusDayByTimeType(today,timeType);
        return fundDailyInfoDao.getFundDailyRangeSimp(fundCode,beginDate,today);
    }

    @Override
    public List<FundDailyInfo> getFundDailyPage(String fundCode, Integer page, Integer size)
    {
        return fundDailyInfoDao.getFundDailyPage(fundCode,page,size);
    }

    @Override
    public List<FundDailySimp> getFundDailyDetailFourPage(String fundCode, Integer page, Integer size)
    {
        return fundDailyInfoDao.getFundDailyDetailFourByCode(fundCode,page,size);
    }

    @Override
    public boolean checkDailyUpdateComplete()
    {
        LocalDate today=LocalDate.now();
        Integer todayUpdateNum=fundDailyInfoDao.getDailyInfoCountByUpdateDate(today);
        Integer allFundNum=fundBasicInfoDao.getFundNumber();
        if(todayUpdateNum>=(allFundNum*0.8))
        {
            return true;
        }
        return false;
    }

    /**
     * if the day is missing, keep it same as its previous day
     * @return
     */

    @Override
    public void missingDateInterpolation()
    {
        List<FundBasicInfo> fundList=fundBasicInfoDao.getAllFund();

        for(FundBasicInfo fundBasicInfo:fundList)
        {
            String fundCode=fundBasicInfo.getFundCode();
            FundDailyInfo furthestDayInfo=fundDailyInfoDao.getFurthestRecord(fundCode);
            if(furthestDayInfo==null)
                continue;
            LocalDate pointerDate=furthestDayInfo.getUpdateDate();
            LocalDate today=LocalDate.now();
            while(pointerDate.isBefore(today))
            {
                FundDailyInfo fundDailyInfoPointer=fundDailyInfoDao.getFundDailyInfoByCodeDate(fundCode,pointerDate);
                LocalDate nextDate=pointerDate.plusDays(1);
                FundDailyInfo fundDailyInfoNext=fundDailyInfoDao.getFundDailyInfoByCodeDate(fundCode,nextDate);
                if(fundDailyInfoNext==null)
                {
                    FundDailyInfo fundDailyInfo=new FundDailyInfo(fundDailyInfoPointer.getFundCode(),fundDailyInfoPointer.getTotalFundVol(),fundDailyInfoPointer.getNAV(),
                            nextDate,fundDailyInfoPointer.getAccumulativeNAV(),fundDailyInfoPointer.getAccumulativePerDiv(),fundDailyInfoPointer.getFundSize(),fundDailyInfoPointer.getFundStatus());
                    fundDailyInfoDao.saveFundDailyInfo(fundDailyInfo);
                }
                pointerDate=nextDate;
            }
        }
    }


}
