package com.fundgroup.backend.service;

public interface RegisterService {

  boolean checkAuth(String deviceId,String auth);

  void setAuth(String deviceId, String auth);
}
