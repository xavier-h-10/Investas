package com.fundgroup.backend.dao;

public interface RegisterDao {

  boolean checkAuth(String deviceId,String auth);

  void setAuth(String deviceId, String auth);
}
