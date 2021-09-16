package com.fundgroup.backend.serviceImpl;


import com.fundgroup.backend.dao.FundRiskAssessmentDao;
import com.fundgroup.backend.entity.FundRiskAssessment;
import com.fundgroup.backend.service.FundRiskAssessmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FundRiskAssessmentServiceImpl implements FundRiskAssessmentService {

  @Autowired
  private FundRiskAssessmentDao fundRiskAssessmentDao;

  @Override
  public List<FundRiskAssessment> getAll() {
    return fundRiskAssessmentDao.getAll();
  }
}
