package com.fundgroup.backend.dao;

import com.fundgroup.backend.entity.UserAuthority;

public interface UserAuthorityDao {
  UserAuthority findUserAuthorityByUsername(String username);

  UserAuthority save(UserAuthority userAuthority);

  void updatePassword(Long userId, String password);

  UserAuthority getById(long userId);

  void deleteUser(Long userId);

  void restoreUser(Long userId);
}
