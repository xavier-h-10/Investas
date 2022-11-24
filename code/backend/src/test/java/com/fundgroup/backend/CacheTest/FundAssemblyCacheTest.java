package com.fundgroup.backend.CacheTest;

import com.fundgroup.backend.cache.FundAssemblyCache;
import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dao.FundDailyInfoDao;
import com.fundgroup.backend.dao.FundRateDao;
import com.fundgroup.backend.dao.HomeRankFundDao;
import com.fundgroup.backend.dto.FundAssembly;
import com.fundgroup.backend.dto.FundDailySimp;
import com.fundgroup.backend.dto.FundPredictionView;
import com.fundgroup.backend.dto.FundRateRank;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundRate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FundAssemblyCacheTest {
    @Mock
    private FundBasicInfoDao fundBasicInfoDao;

    @Mock
    private FundRateDao fundRateDao;

    @Mock
    private FundDailyInfoDao fundDailyInfoDao;

    @Mock
    private HomeRankFundDao homeRankFundDao;

    @InjectMocks
    private FundAssemblyCache fundAssemblyCache;

    @Before
    public void setUp()
    {
        List<FundBasicInfo> fundBasicInfos=new ArrayList<>();
        List<FundDailySimp> fundDailySimps=new ArrayList<>();
        List<FundRateRank> fundRateRanks=new ArrayList<>();
        fundBasicInfos.add(new FundBasicInfo("000001","a","a","a","a","a", LocalDate.now(),LocalDate.now(),1,1,1.0,1.0, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,"a"));
        fundDailySimps.add(new FundDailySimp(LocalDate.now(),1.0,1.0));
        fundRateRanks.add(new FundRateRank("000001",1,1,1,1,1,1,1,1,1,1,1));
        Mockito.when(fundBasicInfoDao.getAllFund()).thenReturn(fundBasicInfos);
        Mockito.when(fundRateDao.getFundRateByCode("000001")).thenReturn(new FundRate("000001", LocalDateTime.now(),1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0));
        Mockito.when(fundDailyInfoDao.getFundDailyDetailFourByCode("000001",0,1)).thenReturn(fundDailySimps);
        Mockito.when(homeRankFundDao.getPredictionViewByFundCode("000001")).thenReturn(new FundPredictionView());
        Mockito.when(fundRateDao.getAllFundRateRank()).thenReturn(fundRateRanks);

        Mockito.when(fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_ONE_DAY)).thenReturn(1);
        Mockito.when(fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_ONE_WEEK)).thenReturn(1);
        Mockito.when(fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_ONE_MONTH)).thenReturn(1);
        Mockito.when(fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_THREE_MONTHS)).thenReturn(1);
        Mockito.when(fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_SIX_MONTHS)).thenReturn(1);
        Mockito.when(fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_ONE_YEAR)).thenReturn(1);
        Mockito.when(fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_TWO_YEARS)).thenReturn(1);
        Mockito.when(fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_THREE_YEARS)).thenReturn(1);
        Mockito.when(fundBasicInfoDao.countFundNumTimeRange(TimeType.LAST_FIVE_YEARS)).thenReturn(1);
        Mockito.when(fundBasicInfoDao.countFundNumTimeRange(TimeType.FROM_BEGINNING)).thenReturn(1);
        Mockito.when(fundBasicInfoDao.countFundNumTimeRange(TimeType.FROM_THIS_YEAR)).thenReturn(1);

    }

    @Test
    public void loadAndUpdate()
    {

        fundAssemblyCache.loadData();

        fundAssemblyCache.updateFundRate();
        fundAssemblyCache.updateFundPrediction();
        fundAssemblyCache.updateFundDailyInfo();
        fundAssemblyCache.updateFundRank();

        fundAssemblyCache.getFundAssembly("000001");
        fundAssemblyCache.getFundArchiveByFundCode("000001");
        fundAssemblyCache.getFundPredictionView("000001");
        fundAssemblyCache.getFundRateTotalCount();
        fundAssemblyCache.getFundRateRankHalfByCode("000001");
    }

}
