package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.UserAuthorityDaoImpl;
import com.fundgroup.backend.entity.UserAuthority;
import com.fundgroup.backend.repository.UserAuthorityRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserAuthorityDaoTest {
	@Mock
	UserAuthorityRepository userAuthorityRepository;

	@InjectMocks
	UserAuthorityDaoImpl userAuthorityDao;


	UserAuthority userAuthority;

	@Before
	public void setUp() throws Exception {
		userAuthority = new UserAuthority();
		userAuthority.setUsername("user");
		userAuthority.setPassword("123456");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

		MockitoAnnotations.initMocks(this);
		Mockito.when(userAuthorityRepository.findUserAuthorityByUsername("user")).thenReturn(null);
		Mockito.when(userAuthorityRepository.save(userAuthority)).thenReturn(null);
		Mockito.when(userAuthorityRepository.getById(1L)).thenReturn(new UserAuthority());
	}

	@Test
	public void testFindUserAuthorityByUsername() {
		Object ret = userAuthorityDao.findUserAuthorityByUsername("user");
		Assert.assertNull(ret);
		Mockito.verify(userAuthorityRepository).findUserAuthorityByUsername("user");
	}

	@Test
	public void testSave() {
		Object ret = userAuthorityDao.save(userAuthority);
		Assert.assertNull(ret);
	}

	@Test
	public void testUpdatePassword() {
		userAuthorityDao.updatePassword(1L, "123456");
	}

	@Test
	public void testGetById() {
		userAuthorityDao.getById(1L);
	}


}
