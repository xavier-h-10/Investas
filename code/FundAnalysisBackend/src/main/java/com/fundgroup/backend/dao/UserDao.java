package com.fundgroup.backend.dao;

import com.fundgroup.backend.dto.FullUserInfo;
import com.fundgroup.backend.entity.User;
import java.util.List;
import java.util.Map;

public interface UserDao {
  void save(User user);

  User findUserByEmail(String email);

  User findUserByUserId(Long userId);

  void updateUserInfo(Long userId, Map<String,String> params);

  List<FullUserInfo> getFullUserInfo(String admin);

  boolean changeUserInfo(Long userId, String introduction, String nickname);

  boolean upgradeToSuperUser(Long userId);

  void deleteUser(Long userId);
}
