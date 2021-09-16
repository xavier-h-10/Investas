package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.FundPortfolioDao;
import com.fundgroup.backend.entity.FundPortfolio;
import com.fundgroup.backend.repository.FundPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FundPortfolioDaoImpl implements FundPortfolioDao {
    @Autowired
    private FundPortfolioRepository fundPortfolioRepository;

    @Override
    public List<FundPortfolio> getFundPortfolioByCode(String fundCode)
    {
        return fundPortfolioRepository.getFundPortfolioByCode(fundCode);
    }
}
