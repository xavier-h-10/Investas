package com.fundgroup.backend.ServiceTest;

import com.fundgroup.backend.dao.RoleDao;
import com.fundgroup.backend.entity.Role;
import com.fundgroup.backend.serviceImpl.RoleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RoleServiceTest {
	@Mock
	RoleDao roleDao;

	@InjectMocks
	RoleServiceImpl roleService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getRoleById() {
		roleService.getRoleById(1);
	}
}
