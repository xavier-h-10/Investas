package com.fundgroup.backend.cache;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.dao.FundRateDao;
import com.fundgroup.backend.dao.HomeRankFundDao;
import com.fundgroup.backend.dto.*;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundDailyInfo;
import com.fundgroup.backend.entity.FundRate;
import com.fundgroup.backend.service.SearchService;
import com.fundgroup.backend.serviceImpl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;

import javax.annotation.PostConstruct;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FundAssemblyCache {
    private HashMap<String, FundAssembly> data=new HashMap<>();
    private FundRateRank fundRateTotalCount;

    @Autowired
    private FundBasicInfoDao fundBasicInfoDao;

    @Autowired
    private FundRateDao fundRateDao;

    @Autowired
    private FundDailyInfoDao fundDailyInfoDao;

    @Autowired
    private HomeRankFundDao homeRankFundDao;

    @Autowired
    private SearchService searchService;

    private void loadFromDatabase(HashMap<String, FundAssembly> data)
    {
        List<FundBasicInfo> fundBasicInfos=fundBasicInfoDao.getAllFund();
        System.out.println(fundBasicInfos.size());
        List<FundSearch> fundSearches = new ArrayList<>();

        for(FundBasicInfo fundBasicInfo : fundBasicInfos)
        {

            FundSearch fundSearch = new FundSearch(fundBasicInfo.getFundCode(), fundBasicInfo.getFundName());
            fundSearches.add(fundSearch);

            FundAssembly fundAssembly =new FundAssembly(fundBasicInfo.getFundCode());
            fundAssembly.setFundName(fundBasicInfo.getFundName());
            fundAssembly.setFundType(fundBasicInfo.getFundType());
            fundAssembly.setAssetSize(fundBasicInfo.getAssetSize());
            fundAssembly.setFundEstablishDate(fundBasicInfo.getFundEstablishDate());
            fundAssembly.setFundRating(fundBasicInfo.getFundRating());
            //load fund rate
            FundRate fundRate=fundRateDao.getFundRateByCode(fundBasicInfo.getFundCode());
            if(fundRate!=null)
            {
                fundAssembly.setLastOneDayRate(fundRate.getLastOneDay());
                fundAssembly.setLastOneWeekRate(fundRate.getLastOneWeek());
                fundAssembly.setLastOneMonthRate(fundRate.getLastOneMonth());
                fundAssembly.setLastThreeMonthRate(fundRate.getLastThreeMonths());
                fundAssembly.setLastSixMonthRate(fundRate.getLastSixMonths());
                fundAssembly.setLastOneYearRate(fundRate.getLastOneYear());
                fundAssembly.setFromBeginningRate(fundRate.getFromBeginning());
            }
            //load daily info
            List<FundDailySimp> fundDailySimp=fundDailyInfoDao.getFundDailyDetailFourByCode(fundBasicInfo.getFundCode(),0,1);
            if(fundDailySimp!=null&&fundDailySimp.size()==1)
            {
                fundAssembly.setNAV(fundDailySimp.get(0).getNAV());
                fundAssembly.setAccumulativeNAV(fundDailySimp.get(0).getAccumulateNAV());
                fundAssembly.setUpdateDate(fundDailySimp.get(0).getUpdateDate());
            }
            //load prediction
            FundPredictionView fundPredictionView= homeRankFundDao.getPredictionViewByFundCode(fundBasicInfo.getFundCode());
            fundAssembly.setFundPredictionView(fundPredictionView);

            data.put(fundAssembly.getFundCode(), fundAssembly);
        }
        searchService.load(fundSearches);
        List<FundRateRank> fundRateRanks=fundRateDao.getAllFundRateRank();
        for(FundRateRank fundRateRank:fundRateRanks)
        {
            String fundCode=fundRateRank.getFundCode();
            if(data.containsKey(fundCode))
            {
                FundAssembly fundAssembly=data.get(fundCode);
                fundAssembly.setLastOneDayRank(fundRateRank.getLastOneDay());
                fundAssembly.setLastOneWeekRank(fundRateRank.getLastOneWeek());
                fundAssembly.setLastOneMonthRank(fundRateRank.getLastOneMonth());
                fundAssembly.setLastThreeMonthRank(fundRateRank.getLastThreeMonths());
                fundAssembly.setLastSixMonthRank(fundRateRank.getLastSixMonths());
                fundAssembly.setLastOneYearRank(fundRateRank.getLastOneYear());
                fundAssembly.setLastTwoYearsRank(fundRateRank.getLastTwoYears());
                fundAssembly.setLastThreeYearsRank(fundRateRank.getLastThreeYears());
                fundAssembly.setLastFiveYearsRank(fundRateRank.getLastFiveYears());
                fundAssembly.setFromBeginningRank(fundRateRank.getFromBeginning());
                fundAssembly.setFromThisYearRank(fundRateRank.getFromThisYear());
            }
        }


        Integer lastOneDay = fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_ONE_DAY);;
        Integer lastOneWeek = fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_ONE_WEEK);
        Integer lastOneMonth= fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_ONE_MONTH);
        Integer lastThreeMonths = fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_THREE_MONTHS);
        Integer lastSixMonths = fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_SIX_MONTHS);
        Integer lastOneYear = fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_ONE_YEAR);
        Integer lastTwoYears = fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_TWO_YEARS);
        Integer lastThreeYears= fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_THREE_YEARS);
        Integer lastFiveYears= fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_FIVE_YEARS);
        Integer fromBeginning= fundBasicInfoDao.countFundNumTimeRange(TimeType.FROM_BEGINNING);
        Integer fromThisYear= fundBasicInfoDao.countFundNumTimeRange(TimeType.FROM_THIS_YEAR);
        fundRateTotalCount=new FundRateRank(null,lastOneDay,lastOneWeek,lastOneMonth,lastThreeMonths,lastSixMonths,
                lastOneYear,lastTwoYears,lastThreeYears,lastFiveYears,fromBeginning,fromThisYear);

        System.out.println(data.size());
    }

   @PostConstruct
   public void loadData()
   {
       loadFromDatabase(data);
   }

    public void updateFundDailyInfo()
    {
        for(Map.Entry<String, FundAssembly> entry : data.entrySet())
        {
            String fundCode=entry.getKey();
            FundAssembly fundAssembly=entry.getValue();
            List<FundDailySimp> fundDailySimp=fundDailyInfoDao.getFundDailyDetailFourByCode(fundCode,0,1);
            if(fundDailySimp!=null&&fundDailySimp.size()==1)
            {
                fundAssembly.setNAV(fundDailySimp.get(0).getNAV());
                fundAssembly.setAccumulativeNAV(fundDailySimp.get(0).getAccumulateNAV());
                fundAssembly.setUpdateDate(fundDailySimp.get(0).getUpdateDate());
            }
        }
    }

    public List<FundSearch> search(String search, Integer page, Integer size) {
        return searchService.search(search, page, size);
    }

    public void updateFundRate()
    {
        for(Map.Entry<String, FundAssembly> entry : data.entrySet())
        {
            String fundCode = entry.getKey();
            FundAssembly fundAssembly = entry.getValue();
            FundRate fundRate=fundRateDao.getFundRateByCode(fundCode);
            if(fundRate!=null)
            {
                fundAssembly.setLastOneDayRate(fundRate.getLastOneDay());
                fundAssembly.setLastOneWeekRate(fundRate.getLastOneWeek());
                fundAssembly.setLastOneMonthRate(fundRate.getLastOneMonth());
                fundAssembly.setLastThreeMonthRate(fundRate.getLastThreeMonths());
                fundAssembly.setLastSixMonthRate(fundRate.getLastSixMonths());
                fundAssembly.setLastOneYearRate(fundRate.getLastOneYear());
            }
        }
    }

    public void updateFundPrediction() {
        for (Map.Entry<String, FundAssembly> entry : data.entrySet())
        {
            String fundCode = entry.getKey();
            FundAssembly fundAssembly = entry.getValue();
            //load prediction
            FundPredictionView fundPredictionView= homeRankFundDao.getPredictionViewByFundCode(fundCode);
            fundAssembly.setFundPredictionView(fundPredictionView);

        }
    }

    public void updateFundRank(){
        Integer lastOneDay = fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_ONE_DAY);;
        Integer lastOneWeek = fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_ONE_WEEK);
        Integer lastOneMonth= fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_ONE_MONTH);
        Integer lastThreeMonths = fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_THREE_MONTHS);
        Integer lastSixMonths = fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_SIX_MONTHS);
        Integer lastOneYear = fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_ONE_YEAR);
        Integer lastTwoYears = fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_TWO_YEARS);
        Integer lastThreeYears= fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_THREE_YEARS);
        Integer lastFiveYears= fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_FIVE_YEARS);
        Integer fromBeginning= fundBasicInfoDao.countFundNumTimeRange(TimeType.FROM_BEGINNING);
        Integer fromThisYear= fundBasicInfoDao.countFundNumTimeRange(TimeType.FROM_THIS_YEAR);
        fundRateTotalCount=new FundRateRank(null,lastOneDay,lastOneWeek,lastOneMonth,lastThreeMonths,lastSixMonths,
                lastOneYear,lastTwoYears,lastThreeYears,lastFiveYears,fromBeginning,fromThisYear);
    }

    public FundAssembly getFundAssembly(String fundCode)
    {
        return data.get(fundCode);
    }

    public FundArchive getFundArchiveByFundCode(String fundCode)
    {
        FundAssembly fundAssembly=data.get(fundCode);
        return new FundArchive(fundAssembly.getFundCode(),fundAssembly.getAssetSize(),fundAssembly.getFundEstablishDate());
    }

    public FundPredictionView getFundPredictionView(String fundCode){
        FundAssembly fundAssembly=data.get(fundCode);
        if(fundAssembly==null)
            return null;
        return fundAssembly.getFundPredictionView();
    }

    public FundRateRank getFundRateTotalCount()
    {
        return fundRateTotalCount;
    }

    public FundRateRank getFundRateRankHalfByCode(String fundCode)
    {
        FundAssembly fundAssembly = data.get(fundCode);
        return new FundRateRank(null,fundAssembly.getLastOneDayRank(),fundAssembly.getLastOneWeekRank(),fundAssembly.getLastOneMonthRank(),
                fundAssembly.getLastThreeMonthRank(),fundAssembly.getLastSixMonthRank(),fundAssembly.getLastOneYearRank(),fundAssembly.getLastTwoYearsRank(),fundAssembly.getLastThreeYearsRank(),fundAssembly.getLastFiveYearsRank(),fundAssembly.getFromBeginningRank(),fundAssembly.getFromThisYearRank());
    }
}
