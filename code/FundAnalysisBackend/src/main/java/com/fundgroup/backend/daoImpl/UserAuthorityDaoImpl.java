package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.UserAuthorityDao;
import com.fundgroup.backend.entity.UserAuthority;
import com.fundgroup.backend.repository.UserAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserAuthorityDaoImpl implements UserAuthorityDao {

  @Autowired
  UserAuthorityRepository userAuthorityRepository;

//  @Autowired
//  void setUserAuthorityRepository(UserAuthorityRepository userAuthorityRepository){
//    this.userAuthorityRepository = userAuthorityRepository;
//  }

  @Override
  public UserAuthority findUserAuthorityByUsername(String username) {
    return userAuthorityRepository.findUserAuthorityByUsername(username);
  }

  @Override
  public UserAuthority save(UserAuthority userAuthority){
//    在初始开发时在Dao层进行加密，后续可以根据功能和安全在其他层加密
//    加密迭代次数，越多越安全，但是可能占据更多资源
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    userAuthority.setPassword(encoder.encode(userAuthority.getPassword()));
    return userAuthorityRepository.save(userAuthority);
  }

  @Override
  public void updatePassword(Long userId,String password) {
    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    String newPassword=encoder.encode(password);
    userAuthorityRepository.updatePasswordByUserId(userId,newPassword);
  }

  @Override
  public UserAuthority getById(long userId) {
    UserAuthority ret = userAuthorityRepository.getById(userId);
    ret.setPassword("User's password was masked by security system");
    return ret;
  }

  @Override
  public void deleteUser(Long userId) {
    UserAuthority userAuthority = userAuthorityRepository.findById(userId).orElse(null);
    if(userAuthority == null){
      return;
    }
    userAuthority.setAccountNonExpired(false);
    userAuthorityRepository.saveAndFlush(userAuthority);
  }

  @Override
  public void restoreUser(Long userId) {
    UserAuthority userAuthority = userAuthorityRepository.findById(userId).orElse(null);
    if(userAuthority == null){
      return;
    }
    userAuthority.setAccountNonExpired(true);
    userAuthorityRepository.saveAndFlush(userAuthority);
  }
}
