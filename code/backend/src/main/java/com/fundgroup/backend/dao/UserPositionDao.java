package com.fundgroup.backend.dao;

import com.fundgroup.backend.entity.UserPosition;
import java.time.LocalDate;
import java.util.List;

public interface UserPositionDao {
  void save(Long userId, String fundCode, LocalDate beginDate,
      Double startPrice, Double amount);

  UserPosition getUserPositionByUserIdAndFundCode(Long userId, String fundCode);
  List<UserPosition> getPositions(LocalDate now, Long userId);
  int delUserPositionByUserIdAndFundCode(Long userId, String fundCode);
}
