package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.dao.UserAuthorityDao;
import com.fundgroup.backend.dao.UserDao;
import com.fundgroup.backend.dto.FullUserInfo;
import com.fundgroup.backend.entity.User;
import com.fundgroup.backend.entity.UserAuthority;
import com.fundgroup.backend.repository.UserRepository;
import com.fundgroup.backend.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

// 由spring security 支持
@Service
public class UserServiceImpl implements UserService {

    UserDao userDao;

    UserAuthorityDao userAuthorityDao;

    @Autowired
    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    void setUserAuthorityDao(UserAuthorityDao userAuthorityDao) {
        this.userAuthorityDao = userAuthorityDao;
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public User findUserByUserId(Long userId) {
        return userDao.findUserByUserId(userId);
    }

    @Override
    public Long getUserId() {
        //    security context holder: 保持安全上下文信息
//    可选的方案是如果要get user info失败，那就直接跳转到登录
//    即不对这个url放行，那么就可以少很多工作
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();//获取用户信息

            //获取登录的用户名
            String username = authentication.getName();
            System.out.println("username : " + username);

            //用户的所有权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            System.out.println("authorities : " + authorities);

            //用户的基本信息
            UserAuthority user = (UserAuthority) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            Long userId = user.getUserId();
            System.out.println("userId: " + userId);

            return userId;
        } else {
            return -1L;
        }
    }

    @Override
    public FullUserInfo getSelfInfo() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            FullUserInfo fullUserInfo = new FullUserInfo();

            //用户的基本信息
            UserAuthority userAuthority = (UserAuthority) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
            User user = userDao.findUserByUserId(userAuthority.getUserId());

            fullUserInfo.setUserId(user.getUserId());
            fullUserInfo.setUsername(userAuthority.getUsername());
            fullUserInfo.setEmail(user.getEmail());
            fullUserInfo.setNickname(user.getNickname());
            fullUserInfo.setStatus(user.getStatus());
            fullUserInfo.setAccountNonExpired(userAuthority.isAccountNonExpired());
            fullUserInfo.setAvatarUrl(user.getAvatarUrl());
            fullUserInfo.setRoles(userAuthority.getRoles());
            fullUserInfo.setIntroduction(user.getIntroduction());
            fullUserInfo.setRiskLevel(user.getRiskLevel());

            return fullUserInfo;
        } else {
            return null;
        }
    }

    @Override
    public void updateUserInfo(Map<String, String> params) {
        Long userId = getUserId();
        if (params.containsKey("password")) {
            String password = params.get("password");
            userAuthorityDao.updatePassword(userId, password);
        }

        userDao.updateUserInfo(userId,params);
    }

    @Override
    public List<FullUserInfo> getFullUserInfo(String admin) {
        return userDao.getFullUserInfo(admin);
    }

    @Override
    public void changePassword(String email, String password) {
        User tmp = userDao.findUserByEmail(email);
        Long userId = tmp.getUserId();
        if (userId != null) {
            userAuthorityDao.updatePassword(userId, password);
        }
    }

    @Override
    public boolean changeUserInfo(Long userId, String introduction, String nickname) {
        return userDao.changeUserInfo(userId, introduction, nickname);
    }

    @Override
    public boolean upgradeToSuperUser(Long userId) {
        return userDao.upgradeToSuperUser(userId);
    }

    @Override
    public void deleteUser(Long userId) {
        userAuthorityDao.deleteUser(userId);
    }

    @Override
    public void restoreUser(Long userId) {
        userAuthorityDao.restoreUser(userId);
    }
}
