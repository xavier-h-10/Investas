package com.fundgroup.backend.service;

import com.fundgroup.backend.dto.MonitorTimeNum;
import com.fundgroup.backend.entity.FundPredictionError;
import java.util.List;

public interface FundPredictionErrorService {

  List<MonitorTimeNum> getErrorMonitorData();

  List<FundPredictionError> getErrorMax();

  List<FundPredictionError> getErrorMin();

  List<FundPredictionError> getErrorByCode(String fundCode);
}
