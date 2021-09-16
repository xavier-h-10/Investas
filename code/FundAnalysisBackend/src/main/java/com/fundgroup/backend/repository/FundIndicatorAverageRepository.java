package com.fundgroup.backend.repository;

import com.fundgroup.backend.entity.FundIndicatorAverage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FundIndicatorAverageRepository extends
    JpaRepository<FundIndicatorAverage, String> {
  FundIndicatorAverage getFundIndicatorAverageByFundType(@Param("fundType") Integer fundType);

  @Query(value = "select f from FundIndicatorAverage f")
  List<FundIndicatorAverage> getAll();
}
