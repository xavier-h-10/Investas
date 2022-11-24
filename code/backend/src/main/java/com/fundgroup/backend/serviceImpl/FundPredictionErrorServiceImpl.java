package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.dao.FundPredictionErrorDao;
import com.fundgroup.backend.dto.MonitorTimeNum;
import com.fundgroup.backend.entity.FundPredictionError;
import com.fundgroup.backend.service.FundPredictionErrorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

@Service
public class FundPredictionErrorServiceImpl implements FundPredictionErrorService{

  @Autowired
  FundPredictionErrorDao fundPredictionErrorDao;

  @Override
  public List<MonitorTimeNum> getErrorMonitorData() {
    return fundPredictionErrorDao.getErrorMonitorData();
  }

  @Override
  public List<FundPredictionError> getErrorMax() {
    return fundPredictionErrorDao.getErrorMax();
  }

  @Override
  public List<FundPredictionError> getErrorMin() {
    return fundPredictionErrorDao.getErrorMin();
  }

  @Override
  public List<FundPredictionError> getErrorByCode(String fundCode) {
    return fundPredictionErrorDao.getErrorByCode(fundCode);
  }
}
