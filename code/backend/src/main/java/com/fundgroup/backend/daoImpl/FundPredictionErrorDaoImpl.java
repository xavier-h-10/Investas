package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.FundPredictionErrorDao;
import com.fundgroup.backend.dto.MonitorTimeNum;
import com.fundgroup.backend.entity.FundPredictionError;
import com.fundgroup.backend.repository.FundPredictionErrorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class FundPredictionErrorDaoImpl implements FundPredictionErrorDao {

  @Autowired
  FundPredictionErrorRepository fundPredictionErrorRepository;

  @Override
  public List<MonitorTimeNum> getErrorMonitorData() {
    return fundPredictionErrorRepository.getErrorMonitorData();
  }

  @Override
  public List<FundPredictionError> getErrorMax() {
    return fundPredictionErrorRepository.getErrorMax();
  }

  @Override
  public List<FundPredictionError> getErrorMin() {
    return fundPredictionErrorRepository.getErrorMin();
  }

  @Override
  public List<FundPredictionError> getErrorByCode(String fundCode) {
    return fundPredictionErrorRepository.getErrorByCode(fundCode);
  }
}
