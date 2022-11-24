package com.fundgroup.backend.repository;

import com.fundgroup.backend.dto.MonitorTimeNum;
import com.fundgroup.backend.entity.FundPredictionError;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

public interface FundPredictionErrorRepository extends JpaRepository<FundPredictionError, BigInteger> {

  @Query(value = "select new com.fundgroup.backend.dto.MonitorTimeNum(f.lastUpdateTimestamp, "
      + "count(f.fundCode))"
      + "from FundPredictionError f group by f.lastUpdateTimestamp")
  List<MonitorTimeNum> getErrorMonitorData();

  @Query(value = "select * from fundSystem.fund_prediction_error"
      + " order by abs(fundSystem.fund_prediction_error.today_abs_delta)"
      + " desc limit 100", nativeQuery = true)
  List<FundPredictionError> getErrorMax();

  @Query(value = "select * from fundSystem.fund_prediction_error"
      + " order by abs(fundSystem.fund_prediction_error.today_abs_delta)"
      + " limit 100", nativeQuery = true)
  List<FundPredictionError> getErrorMin();

  @Query(value = "from FundPredictionError f where f.fundCode = :fundCode order by "
      + "f.lastUpdateTimestamp desc")
  List<FundPredictionError> getErrorByCode(@RequestParam("fundCode") String fundCode);
}
