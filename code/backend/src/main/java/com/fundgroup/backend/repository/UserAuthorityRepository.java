package com.fundgroup.backend.repository;


import com.fundgroup.backend.entity.Role;
import com.fundgroup.backend.entity.UserAuthority;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
    UserAuthority findUserAuthorityByUsername(String username);

    @Modifying
    @Query(value = "update user_auth set password=?2 where user_id=?1", nativeQuery = true)
    void updatePasswordByUserId(Long userId, String password);

    @Query(value = "select * from fundSystem.user_auth as auth where auth.user_id in "
        + "(select user_auth_user_id from fundsystem.user_auth_roles as roles where roles.roles_role_id=?1)", nativeQuery = true)
    List<UserAuthority> getAllByRolesHas(Integer roleId);
}
