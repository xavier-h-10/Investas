package com.fundgroup.backend.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.dao.FundRateDao;
import com.fundgroup.backend.dto.FundBasicInfoAndRecentRate;
import com.fundgroup.backend.dto.FundRankingByRecentRate;
import com.fundgroup.backend.dto.RateEntry;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundDailyInfo;
import com.fundgroup.backend.entity.FundRate;
import com.fundgroup.backend.dto.FundRateRecent;
import com.fundgroup.backend.service.FundRateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Log4j2
public class FundRateServiceImpl implements FundRateService {

    @Autowired
    private FundBasicInfoDao fundBasicInfoDao;

    @Autowired
    private FundDailyInfoDao fundDailyInfoDao;

    @Autowired
    private FundRateDao fundRateDao;

    private List<RateEntry> rateEntryList = new ArrayList<>(Arrays.asList(new RateEntry(ChronoUnit.DAYS, 1),
            new RateEntry(ChronoUnit.DAYS, 7), new RateEntry(ChronoUnit.MONTHS, 1), new RateEntry(ChronoUnit.MONTHS, 3),
            new RateEntry(ChronoUnit.MONTHS, 6), new RateEntry(ChronoUnit.YEARS, 1), new RateEntry(ChronoUnit.YEARS, 2),
            new RateEntry(ChronoUnit.YEARS, 3), new RateEntry(ChronoUnit.YEARS, 5)));

    private LocalDate today = LocalDate.now();

    /**
     * if date is weekends, set to friday
     *
     * @param
     * @return
     */

//    private double calculateCurrencyFundRate(List<FundDailyInfo> fundDailyInfos)
//    {
//        double base=0;
//        int size=fundDailyInfos.size();
//        for(FundDailyInfo fundDailyInfo:fundDailyInfos)
//        {
//            base+=fundDailyInfo.getNAV();
//        }
//        return base/size*365/10000;
//    }


       /**
     * today must be monday to friday
     * range search to handle holiday problem
     * @param
     * @return
     */
    @Override
    public boolean updateFundRate(LocalDate today)
    {
        log.info("fund rate update begin");
        Integer todayUpdateNum=fundDailyInfoDao.getDailyInfoCountByUpdateDate(today);
        Integer allFundNum=fundBasicInfoDao.getFundNumber();
        if(todayUpdateNum<=(allFundNum*0.8))
        {
            log.error("updateFundRate fail, today data missing");
            return false;
        }

        List<FundBasicInfo> fundList=fundBasicInfoDao.getAllFund();
        List<LocalDate> dates=new ArrayList<>();


        for(RateEntry rateEntry : rateEntryList)
        {
            LocalDate tmpDate=today.minus(rateEntry.balance,rateEntry.unit);
            dates.add(tmpDate);
        }

        //from this year
        LocalDate thisYearDate=today.withDayOfYear(1);
        dates.add(thisYearDate);

        for(FundBasicInfo fund : fundList) {
            Integer fundType = fund.getFundType();
            List<Double> fundRates = new ArrayList<>();
            FundDailyInfo todayInfo = fundDailyInfoDao.getFundDailyInfoByCodeDate(fund.getFundCode(), today);
            double fromBeginningRate = -1;


            if (todayInfo == null) {
                continue;
            }


            for (LocalDate beginDate : dates) {
                FundDailyInfo beginInfo = fundDailyInfoDao.getFundDailyInfoByCodeDate(fund.getFundCode(), beginDate);
                //if fail to search daily info
                if (beginInfo == null) {
                    //start range search 10 days
                    Integer rangeDate = 10;
                    LocalDate beginRight = beginDate.plusDays(rangeDate);
                    List<FundDailyInfo> fundDailyInfos = fundDailyInfoDao.getFundDailyRange(fund.getFundCode(), beginDate, beginRight);
                    if (fundDailyInfos.isEmpty()) {
                        fundRates.add((double) -1);
                        continue;
                    } else {
                        //select smallest date found
                        beginInfo = fundDailyInfos.get(0);
                    }

                }
                Double fundRate;
                if (beginInfo.getAccumulativeNAV() == 0) {
                    fundRate = 0.0;
                } else {
                    fundRate = (todayInfo.getAccumulativeNAV() - beginInfo.getAccumulativeNAV()) / beginInfo.getAccumulativeNAV() * 100;
                }

                fundRates.add(fundRate);
            }
            fromBeginningRate = (todayInfo.getAccumulativeNAV() - 1) * 100;

            FundRate fundRate = new FundRate(fund.getFundCode(), LocalDateTime.now(), fundRates.get(0), fundRates.get(1), fundRates.get(2),
                    fundRates.get(3), fundRates.get(4), fundRates.get(5), fundRates.get(6), fundRates.get(7), fundRates.get(8),
                    fromBeginningRate, fundRates.get(9));
            fundRateDao.updateFundRate(fundRate);
        }

        log.info("fund rate update finish");
        return true;
    }

    @Override
    public JSONObject getFundRecRate(String fundCode) {
        FundRateRecent fundRateRecent = fundRateDao.getFundRecRate(fundCode);
        return fundRateRecent.ToJSONObject();
    }

    @Override
    public JSONObject getFundRateByCode(String fundCode) {
        FundRate fundRate = fundRateDao.getFundRateByCode(fundCode);
        return fundRate.ToJSONObject();
    }

    @Override
    public List<FundRankingByRecentRate> getFundByRateRanking(Integer type) {
        List<FundRankingByRecentRate> fundRates = new ArrayList<>();
        if (type == 0) {
            return fundRateDao.getTopFundRateByMonth();
        } else if (type == 1) {
            return fundRateDao.getTopFundRateByYear();
        } else {
            return null;
        }

//        List<FundBasicInfoAndRecentRate> fundBasicInfoAndRecentRates = new ArrayList<>();
//
//        for (FundRate fundRate : fundRates) {
//            FundBasicInfoAndRecentRate fundBasicInfoAndRecentRate = new FundBasicInfoAndRecentRate();
//            FundBasicInfo fundBasicInfo = fundBasicInfoDao.getOne(fundRate.getFundCode());
//            fundBasicInfoAndRecentRate.setFundCode(fundBasicInfo.getFundCode());
//            fundBasicInfoAndRecentRate.setFundType(fundBasicInfo.getFundType());
//            fundBasicInfoAndRecentRate.setLastOneMonthRate(fundRate.getLastOneMonth());
//            fundBasicInfoAndRecentRate.setFundName(fundBasicInfo.getFundName());
//            fundBasicInfoAndRecentRate.setLastOneYearRate(fundRate.getLastOneYear());
//            fundBasicInfoAndRecentRates.add(fundBasicInfoAndRecentRate);
//        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("RateList", JSONObject.toJSON(fundBasicInfoAndRecentRates));
//        return jsonObject;
    }
}
