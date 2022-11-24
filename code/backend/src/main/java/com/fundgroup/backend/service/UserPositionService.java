package com.fundgroup.backend.service;

import com.fundgroup.backend.entity.UserPosition;
import java.util.List;


public interface UserPositionService{

  Integer setPositionByMoney(Long userId, String fundCode, Double sumAmount);

  int setPositionByAmount(Long userId, String fundCode, Double amount);

  List<UserPosition> getPositions(Long userId);

  UserPosition getUserPositionByUserIdAndFundCode(Long userId, String fundCode);
  int delUserPositionByUserIdAndFundCode(Long userId, String fundCode);

}
