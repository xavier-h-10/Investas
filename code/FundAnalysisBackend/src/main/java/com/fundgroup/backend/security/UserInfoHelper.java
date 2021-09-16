package com.fundgroup.backend.security;

import com.fundgroup.backend.dao.UserAuthorityDao;
import com.fundgroup.backend.entity.UserAuthority;
import com.fundgroup.backend.utils.messageUtils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserInfoHelper {

  UserAuthorityDao userAuthorityDao;

  @Autowired
  void setUserAuthorityDao(UserAuthorityDao userAuthorityDao) {
    this.userAuthorityDao = userAuthorityDao;
  }

  // Help with get basic user info
  // Attention: userDetail is defined by Spring security
  private UserDetails getUserDetails() {
    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      return (UserDetails) authentication.getPrincipal();
    }
    return null;
  }

  // Help with user ID
  public Long getUserId() {
    UserDetails userDetails = getUserDetails();

    if (userDetails != null) {
      return userAuthorityDao.findUserAuthorityByUsername(getUserDetails().getUsername()).getUserId();
    } else {
      return -1L;
    }
  }

  public String getUserName() {
    UserDetails userDetails = getUserDetails();

    if (userDetails != null) {
      return (getUserDetails().getUsername());
    } else {
      return null;
    }
  }
}
