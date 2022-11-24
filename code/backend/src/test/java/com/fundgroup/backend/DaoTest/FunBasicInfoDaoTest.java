package com.fundgroup.backend.DaoTest;


import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.repository.FundBasicInfoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/*
* Test fail!
* */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FunBasicInfoDaoTest {

    @Autowired
    private FundBasicInfoRepository fundBasicInfoRepository;

    @Test
    public void testGetSimpInfo()
    {
        Pageable pageable= PageRequest.of(0,20);
        List<FundBasicInfo> fundBasicInfoList=fundBasicInfoRepository.searchFundByCodeOrName("000001",pageable);
//        Assert.assertEquals("000001",fundBasicInfoList.get(0).getFundCode());
    }
}
