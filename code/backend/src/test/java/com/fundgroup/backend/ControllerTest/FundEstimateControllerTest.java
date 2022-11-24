package com.fundgroup.backend.ControllerTest;

import com.fundgroup.backend.constant.TimeType;
import com.fundgroup.backend.controller.FundEstimateController;
import com.fundgroup.backend.service.FundEstimateService;
import com.fundgroup.backend.utils.DateUtils;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FundEstimateControllerTest {
	@Mock
	private FundEstimateService fundEstimateService;

	@InjectMocks
	FundEstimateController fundEstimateController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(fundEstimateService.getTodayEstimateByCode("1")).thenReturn(new ArrayList<>());
		Mockito.when(fundEstimateService.getTodayEstimateByCode("2")).thenReturn(null);
	}

	@Test
	public void getTodayEstimateByCode() {
		fundEstimateController.getTodayEstimateByCode("1");
		fundEstimateController.getTodayEstimateByCode("2");
	}

	@Test
	public void deleteRange() {
		Map<String, Object> params = new HashMap<>();
		params.put("timeType", 1);
		fundEstimateController.deleteRange(params);
	}


}
