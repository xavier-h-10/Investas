package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.DaoTest.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		FundBasicInfoServiceTest.class,
		FundCompetitionDaoTest.class,
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
		CrawlerServiceTest.class
})
public class ServiceSummary {

}
