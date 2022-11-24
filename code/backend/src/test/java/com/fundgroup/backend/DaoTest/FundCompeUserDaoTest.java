package com.fundgroup.backend.DaoTest;

import com.fundgroup.backend.daoImpl.FundCompeUserDaoImpl;
import com.fundgroup.backend.entity.FundCompeUser;
import com.fundgroup.backend.repository.FundCompeUserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class FundCompeUserDaoTest {
    @Mock
    private FundCompeUserRepository fundCompeUserRepository;

    @InjectMocks
    private FundCompeUserDaoImpl fundCompeUserDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(fundCompeUserRepository.getFundCompeUsersByCompetitionId(1)).thenReturn(null);
        Mockito.when(fundCompeUserRepository.getAllByUserId(4L)).thenReturn(null);
        Mockito.when(fundCompeUserRepository.getAllActiveByUserId(4L)).thenReturn(null);
        Mockito.when(fundCompeUserRepository.getFundCompeUserByCompetitionIdAndUserId(1, 4L))
                .thenReturn(null);
        Mockito.when(fundCompeUserRepository.getTopRankByCompetitionId(1, 1))
                .thenReturn(null);
        Mockito.when(fundCompeUserRepository.save(new FundCompeUser())).thenReturn(null);
    }

    @Test
    public void testGetFundCompeUsersByCompetitionId() {
        List<FundCompeUser> ret = fundCompeUserDao.getFundCompeUsersByCompetitionId(1);
        Assert.assertNull(ret);
        Mockito.verify(fundCompeUserRepository).getFundCompeUsersByCompetitionId(1);
    }

    @Test
    public void testUpdateFundCompeUserList() {
        fundCompeUserDao.updateFundCompeUserList(new ArrayList<>());
    }

    @Test
    public void testGetAllByUserId() {
        List<FundCompeUser> ret = fundCompeUserDao.getAllByUserId(4L);
        Assert.assertNull(ret);
        Mockito.verify(fundCompeUserRepository).getAllByUserId(4L);
    }

    @Test
    public void testGetAllActiveByUserId() {
        List<FundCompeUser> ret = fundCompeUserDao.getAllActiveByUserId(4L);
        Assert.assertNull(ret);
        Mockito.verify(fundCompeUserRepository).getAllActiveByUserId(4L);
    }

    @Test
    public void testGetFundCompeUserByCompetitionIdAndUserId() {
        FundCompeUser ret = fundCompeUserDao.getFundCompeUserByCompetitionIdAndUserId(1, 4L);
        Assert.assertNull(ret);
        Mockito.verify(fundCompeUserRepository).getFundCompeUserByCompetitionIdAndUserId(1, 4L);
    }

    @Test
    public void testGetTopRankByCompetitionId() {
        List<Object[]> ret = fundCompeUserDao.getTopRankByCompetitionId(1, 1);
        Assert.assertNull(ret);
        Mockito.verify(fundCompeUserRepository).getTopRankByCompetitionId(1, 1);
    }

    @Test
    public void testSaveFundCompeUser() {
        FundCompeUser ret = fundCompeUserDao.saveFundCompeUser(new FundCompeUser());
        Assert.assertNull(ret);
        Mockito.verify(fundCompeUserRepository).save(new FundCompeUser());
    }

}
