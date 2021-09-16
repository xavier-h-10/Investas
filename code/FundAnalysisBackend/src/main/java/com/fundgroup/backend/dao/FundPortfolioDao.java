package com.fundgroup.backend.dao;

import com.fundgroup.backend.entity.FundPortfolio;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundPortfolioDao {
    List<FundPortfolio> getFundPortfolioByCode(String fundCode);
}
