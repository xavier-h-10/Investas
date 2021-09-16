package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.RoleDaoImpl;
import com.fundgroup.backend.entity.Role;
import com.fundgroup.backend.repository.RoleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class RoleDaoTest {
	@Mock
	RoleRepository roleRepository;

	@InjectMocks
	RoleDaoImpl roleDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(roleRepository.getById(1)).thenReturn(null);
	}

	@Test
	public void testSave() {
		roleDao.save(new Role());
	}

	@Test
	public void testFindOne() {
		Object ret = roleDao.findOne(1);
		Assert.assertNull(ret);
		Mockito.verify(roleRepository).getById(1);
	}

}
