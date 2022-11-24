package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.dao.RegisterDao;
import com.fundgroup.backend.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

  @Autowired
  RegisterDao registerDao;

//  @Autowired
//  void setRegisterDao(RegisterDao registerDao) {
//    this.registerDao = registerDao;
//  }

  @Override
  public boolean checkAuth(String deviceId,String auth) {
    return registerDao.checkAuth(deviceId,auth);
  }

  @Override
  public void setAuth(String deviceId, String auth) {
    registerDao.setAuth(deviceId, auth);
  }
}
