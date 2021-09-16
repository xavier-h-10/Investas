package com.fundgroup.backend.repository;

import com.fundgroup.backend.entity.FundPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundPortfolioRepository extends JpaRepository<FundPortfolio,Integer> {
    @Query("select f from FundPortfolio f where f.fundCode=:fundCode")
    List<FundPortfolio> getFundPortfolioByCode(@Param("fundCode") String fundCode);
}
