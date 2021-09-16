package com.fundgroup.backend.service;

import com.fundgroup.backend.dto.FullUserInfo;
import com.fundgroup.backend.entity.User;
import com.fundgroup.backend.entity.UserAuthority;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

public interface UserService {

  User findUserByEmail(String email);

  User findUserByUserId(Long userId);

  Long getUserId();

  void updateUserInfo(Map<String, String> params);

  FullUserInfo getSelfInfo();

  List<FullUserInfo> getFullUserInfo(String admin);

  void changePassword(String email, String password);

  boolean changeUserInfo(Long userId, String introduction, String nickname);

  boolean upgradeToSuperUser(Long userId);

  void deleteUser(Long userId);

  void restoreUser(Long userId);
}
