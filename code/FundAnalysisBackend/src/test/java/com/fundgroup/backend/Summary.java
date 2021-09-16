package com.fundgroup.backend;

import com.fundgroup.backend.CacheTest.FundAssemblyCacheTest;
import com.fundgroup.backend.CacheTest.StockCacheTest;
import com.fundgroup.backend.ControllerTest.*;
import com.fundgroup.backend.DaoTest.*;
import com.fundgroup.backend.ServiceTest.*;
import com.fundgroup.backend.SpringSecurityTest.SecurityTest;
import com.fundgroup.backend.SpringSecurityTest.VerifyCodeTest;
import com.fundgroup.backend.cache.FundAssemblyCache;
import com.fundgroup.backend.cache.StockCache;
import com.fundgroup.backend.entity.FundPrediction;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
//		UserPositionControllerTest.class,

		FundRateServiceTest.class,
		VerifyCodeTest.class,
		SecurityTest.class,

		FundArchiveDetailControllerTest.class,
//		FundBasicInfoControllerTest.class,
		FundCompetitionControllerTest.class,
		FundDailyInfoControllerTest.class,
		FundEstimateControllerTest.class,
		FundIndicatorControllerTest.class,
		FundInfoControllerTest.class,
		FundManagerInfoControllerTest.class,
		FundPortfolioControllerTest.class,
		FundPredictionControllerTest.class,
		FundPredictionErrorControllerTest.class,
		FundRateControllerTest.class,
		FundRiskAssessmentControllerTest.class,
		FundArchiveDetailControllerTest.class,
//		FundViewControllerTest.class,
		HomeRankFundControllerTest.class,
		MonitorControllerTest.class,
		PredictionDescriptionControllerTest.class,

		//pipeline test
		PipelineScheduleTest.class,
		CustomScheduleTest.class,


		// Dao
		FundBasicInfoDaoTest.class,
		FundCompetitionDaoTest.class,
		FundCompeUserDaoTest.class,
		FundDailyInfoDaoTest.class,
		FundEstimateDaoTest.class,
		FundIndicatorDaoTest.class,
		FundManagerInfoDaoTest.class,
		FundPortfolioDaoTest.class,
		FundPredictionErrorDaoTest.class,
		FundRateDaoTest.class,
		FundRiskAssessmentDaoTest.class,
		HomeRankFundDaoTest.class,
		RoleDaoTest.class,
		StockBasicInfoDaoTest.class,
		StockDailyInfoDaoTest.class,
		UserAuthorityDaoTest.class,
		UserPositionDaoTest.class,


		// Service
		FundBasicInfoServiceTest.class,
		FundCompetitionServiceTest.class,
		FundCompeUserServiceTest.class,
		FundDailyInfoServiceTest.class,
		FundEstimateServiceTest.class,
		FundIndicatorServiceTest.class,
		FundManagerInfoServiceTest.class,
		FundPortfolioServiceTest.class,
		FundPredictionErrorServiceTest.class,
		FundRateServiceTest.class,
		FundRiskAssessmentServiceTest.class,
		RegisterServiceTest.class,
		RoleServiceTest.class,
		StockDailyInfoServiceTest.class,
		StockServiceTest.class,
		UserPositionServiceTest.class,
		CrawlerServiceTest.class,

		//Cache
		FundAssemblyCacheTest.class,
		StockCacheTest.class,

})
public class Summary {

}
