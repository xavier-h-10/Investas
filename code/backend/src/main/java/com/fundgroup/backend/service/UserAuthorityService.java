package com.fundgroup.backend.service;

import com.fundgroup.backend.dao.UserAuthorityDao;
import com.fundgroup.backend.dao.UserDao;
import com.fundgroup.backend.entity.Role;
import com.fundgroup.backend.entity.User;
import com.fundgroup.backend.entity.UserAuthority;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 由spring security 支持
@Service
public class UserAuthorityService implements  UserDetailsService {

  UserAuthorityDao userAuthorityDao;
  UserDao userDao;

  @Autowired
  void setUserAuthorityDao(UserAuthorityDao userAuthorityDao) {
    this.userAuthorityDao = userAuthorityDao;
  }

  @Autowired
  void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserAuthority userAuthority = userAuthorityDao.findUserAuthorityByUsername(username);
    if (userAuthority == null) {
      throw new UsernameNotFoundException("用户不存在");
    }
    return userAuthority;
  }

  public boolean hasSameUserName(String email) {
    return (userAuthorityDao.findUserAuthorityByUsername(email) != null);
  }

  public int register(String email, String username, String password, List<Role> roles) {
//    组装user Auth存下
    UserAuthority userAuthority = new UserAuthority();
    userAuthority.setUsername(email);
    userAuthority.setPassword(password);
    userAuthority.setRoles(roles);
    userAuthority.setEnabled(true);
    userAuthority.setAccountNonExpired(true);
    userAuthority.setAccountNonLocked(true);
    userAuthority.setCredentialsNonExpired(true);

    userAuthority = userAuthorityDao.save(userAuthority);

    User user = new User();
    user.setUserId(userAuthority.getUserId());
    user.setNickname(username);
    user.setEmail(email);
    user.setRiskLevel(0);
    user.setStatus(0);
    user.setAvatarUrl("");

    System.out.println(user.toString());

    userDao.save(user);

    return 0;
  }

  public UserAuthority getById(long userId){
    return userAuthorityDao.getById(userId);
  }

}
