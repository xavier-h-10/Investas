package com.fundgroup.backend.service;

import com.fundgroup.backend.entity.FundPortfolio;

import java.util.List;

public interface FundPortfolioService {
    List<FundPortfolio> getFundPortfolioByCode(String fundCode);
}
