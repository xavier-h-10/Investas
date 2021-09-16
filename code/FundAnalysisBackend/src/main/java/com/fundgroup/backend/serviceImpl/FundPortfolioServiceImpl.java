package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.dao.FundPortfolioDao;
import com.fundgroup.backend.entity.FundPortfolio;
import com.fundgroup.backend.service.FundPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundPortfolioServiceImpl implements FundPortfolioService {
    @Autowired
    private FundPortfolioDao fundPortfolioDao;

    @Override
    public List<FundPortfolio> getFundPortfolioByCode(String fundCode)
    {
        return fundPortfolioDao.getFundPortfolioByCode(fundCode);
    }
}
