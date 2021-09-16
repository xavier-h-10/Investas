package com.fundgroup.backend.ControllerTest;

import com.fundgroup.backend.controller.MonitorController;
import com.fundgroup.backend.entity.FundCompeUser;
import com.fundgroup.backend.repository.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorControllerTest {
	@Mock
	FundPredictionRepository fundPredictionRepository;
	@Mock
	FundDailyInfoRepository fundDailyInfoRepository;
	@Mock
	FundEstimateRepository fundEstimateRepository;
	@Mock
	FundIndicatorRepository fundIndicatorRepository;
	@Mock
	FundBasicInfoRepository fundBasicInfoRepository;
	@Mock
	FundCompetitionRepository fundCompetitionRepository;
	@Mock
	FundRateRepository fundRateRepository;
	@Mock
	FundModelRepository fundModelRepository;

	@InjectMocks
	MonitorController monitorController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getMonitorData() {
		monitorController.getMonitorData();
	}

	@Test
	public void getWorkplaceData() {
		monitorController.getWorkplaceData();
	}

	@Test
	public void setTypeModel() {
		Map<String, String> params = new HashMap<>();
		params.put("id", "1");
		params.put("code", "1");
		monitorController.setTypeModel(params);
	}

	@Test
	public void deleteTypeModel() {
		Map<String, String> params = new HashMap<>();
		params.put("fundType", "1");
		monitorController.deleteTypeModel(params);
	}
}
