package com.fundgroup.backend.DaoTest;


import com.fundgroup.backend.dao.HomeRankFundDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeRankFundDaoTest {
	@Autowired
	HomeRankFundDao homeRankFundDao;

	@Test
	public void test() {
		Assert.assertNull(homeRankFundDao.getHistoryTop(0, 3, -1));
		Assert.assertNull(homeRankFundDao.getPredictionTop(0, 3, -1));
		homeRankFundDao.getPredictionViewByFundCode("1234455");
	}

}