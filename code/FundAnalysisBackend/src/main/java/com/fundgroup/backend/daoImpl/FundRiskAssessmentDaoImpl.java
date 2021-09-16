package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.FundRiskAssessmentDao;
import com.fundgroup.backend.entity.FundRiskAssessment;
import com.fundgroup.backend.repository.FundRiskAssessmentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FundRiskAssessmentDaoImpl implements FundRiskAssessmentDao {

  @Autowired
  private FundRiskAssessmentRepository fundRiskAssessmentRepository;

  @Override
  public List<FundRiskAssessment> getAll() {
    return fundRiskAssessmentRepository.getAll();
  }
}
