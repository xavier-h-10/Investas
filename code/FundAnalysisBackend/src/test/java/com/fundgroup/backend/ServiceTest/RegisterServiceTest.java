package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dao.RegisterDao;
import com.fundgroup.backend.serviceImpl.RegisterServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RegisterServiceTest {
	@Mock
	RegisterDao registerDao;
	@InjectMocks
	RegisterServiceImpl registerService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test() {
		registerService.checkAuth("1", "1");
		registerService.setAuth("1", "1");
	}
}
