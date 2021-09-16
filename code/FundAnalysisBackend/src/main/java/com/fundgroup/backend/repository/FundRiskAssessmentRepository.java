package com.fundgroup.backend.repository;

import com.fundgroup.backend.entity.FundRiskAssessment;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FundRiskAssessmentRepository extends JpaRepository<FundRiskAssessment, String> {

  @Query(value = "select * from fund_risk_assessment order by `order`", nativeQuery = true)
  List<FundRiskAssessment> getAll();
}
