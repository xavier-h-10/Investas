package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.UserDao;
import com.fundgroup.backend.dto.FullUserInfo;
import com.fundgroup.backend.entity.Role;
import com.fundgroup.backend.entity.User;
import com.fundgroup.backend.entity.UserAuthority;
import com.fundgroup.backend.repository.RoleRepository;
import com.fundgroup.backend.repository.UserAuthorityRepository;
import com.fundgroup.backend.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
    UserRepository userRepository;

    UserAuthorityRepository userAuthorityRepository;

    RoleRepository roleRepository;

    @Autowired
    void setRoleRepository(RoleRepository repository){
        this.roleRepository = repository;
    }

    @Autowired
    void setUserAuthorityRepository(UserAuthorityRepository userAuthorityRepository){
        this.userAuthorityRepository = userAuthorityRepository;
    }

    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        System.out.println(user);
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByUserId(Long userId) {
        return userRepository.findUserByUserId(userId);
    }

    @Override
    public void updateUserInfo(Long userId, Map<String, String> params) {
        User user = findUserByUserId(userId);
        if (params.containsKey("nickname")) {
            user.setNickname(params.get("nickname"));
        }
        if (params.containsKey("risk_level")) {
            user.setRiskLevel(Integer.valueOf(params.get("risk_level")));
        }
        if (params.containsKey("introduction")) {
            user.setIntroduction(params.get("introduction"));
        }
        userRepository.save(user);
    }

    @Override
    public List<FullUserInfo> getFullUserInfo(String admin) {
        List<FullUserInfo> fullUserInfos = new ArrayList<>();
        Collection<List<Role>> roles = new ArrayList<>();
        List<Role> tools = new ArrayList<>();
        List<UserAuthority> userAuthorities = new ArrayList<>();
        List<UserAuthority> all = userAuthorityRepository.findAll();

        if(Objects.equals(admin, "user")){
            for(UserAuthority userAuthority: all){
                if(!userAuthority.getRoles().isEmpty() &&( userAuthority.getRoles().get(0).getRoleId() == 3
                || userAuthority.getRoles().get(0).getRoleId() == 4)){
                    userAuthorities.add(userAuthority);
                }
            }
        }
        else{
            for(UserAuthority userAuthority: all){
                if(!userAuthority.getRoles().isEmpty() &&( userAuthority.getRoles().get(0).getRoleId() == 2)){
                    userAuthorities.add(userAuthority);
                }
            }
        }
        System.out.println(userAuthorities.toString());

        for(UserAuthority userAuthority: userAuthorities){
            User user = userRepository.getById(userAuthority.getUserId());
            FullUserInfo fullUserInfo = new FullUserInfo(user.getUserId(), userAuthority.getUsername(),
                userAuthority.isAccountNonExpired(), user.getNickname(),
                user.getRiskLevel(), user.getEmail(), user.getStatus(),
                user.getAvatarUrl(), user.getIntroduction(), userAuthority.getRoles());
            fullUserInfos.add(fullUserInfo);
        }

        return fullUserInfos;
    }

    @Override
    public boolean changeUserInfo(Long userId, String introduction, String nickname) {
        User user = userRepository.findById((userId)).orElse(null);
        if(user == null){
            return false;
        }
        else{
            user.setNickname(nickname);
            user.setIntroduction(introduction);
            userRepository.saveAndFlush(user);
            return true;
        }
    }

    @Override
    public boolean upgradeToSuperUser(Long userId) {
        UserAuthority user = userAuthorityRepository.findById(userId).orElse(null);
        if(user == null || user.getRoles().get(0).getRoleId() != 4){
            return false;
        }
        else{
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.getById(3));
            user.setRoles(roles);
            userAuthorityRepository.saveAndFlush(user);
            return true;
        }
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


}
