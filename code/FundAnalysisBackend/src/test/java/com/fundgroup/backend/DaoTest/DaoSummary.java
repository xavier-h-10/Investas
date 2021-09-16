package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.ControllerTest.FundArchiveDetailControllerTest;
import com.fundgroup.backend.ControllerTest.UserPositionControllerTest;
import com.fundgroup.backend.ServiceTest.FundCompeUserServiceTest;
import com.fundgroup.backend.ServiceTest.FundRateServiceTest;
import com.fundgroup.backend.SpringSecurityTest.SecurityTest;
import com.fundgroup.backend.SpringSecurityTest.VerifyCodeTest;
import com.fundgroup.backend.dao.FundBasicInfoDao;
import com.fundgroup.backend.dao.FundCompetitionDao;
import com.fundgroup.backend.dao.FundEstimateDao;
import com.fundgroup.backend.dao.FundIndicatorDao;
import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.FundCompetition;
import com.fundgroup.backend.entity.FundDailyInfo;
import com.fundgroup.backend.entity.FundEstimate;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
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
		RoleDaoTest.class,
		StockBasicInfoDaoTest.class,
		StockDailyInfoDaoTest.class,
		UserAuthorityDaoTest.class,
		UserPositionDaoTest.class
})
public class DaoSummary {

}
