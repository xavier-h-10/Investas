package com.fundgroup.backend.DataInit;
import com.fundgroup.backend.dao.UserAuthorityDao;
import com.fundgroup.backend.entity.Role;
import com.fundgroup.backend.entity.UserAuthority;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseTests {

  @Autowired
  UserAuthorityDao userAuthorityDao;

  @Test
  void contextLoads() {
//    创建四类角色
    Role role1 = new Role();
    Role role2 = new Role();
    Role role3 = new Role();
    Role role4 = new Role();

//    创建四类用户
//    super admin
    role1.setRoleNameZH("系统管理员");
    role1.setRoleNameEN("ROLE_superAdmin");

    UserAuthority u1 = new UserAuthority();
    u1.setUsername("superAdmin@jwz.org");
    u1.setPassword("123456");
    u1.setAccountNonExpired(true);
    u1.setAccountNonLocked(true);
    u1.setCredentialsNonExpired(true);
    u1.setEnabled(true);
    List<Role> rs1 = new ArrayList<>();
    rs1.add(role1);
    u1.setRoles(rs1);
    userAuthorityDao.save(u1);

//    admin
    role2.setRoleNameZH("管理员");
    role2.setRoleNameEN("ROLE_admin");

    UserAuthority u2 = new UserAuthority();
    u2.setUsername("admin@jwz.org");
    u2.setPassword("123456");
    u2.setAccountNonExpired(true);
    u2.setAccountNonLocked(true);
    u2.setCredentialsNonExpired(true);
    u2.setEnabled(true);
    List<Role> rs2 = new ArrayList<>();
    rs2.add(role2);
    u2.setRoles(rs2);
    userAuthorityDao.save(u2);

    role3.setRoleNameZH("超级会员");
    role3.setRoleNameEN("ROLE_superUser");

    UserAuthority u3 = new UserAuthority();
    u3.setUsername("superUser@sjtu.edu.cn");
    u3.setPassword("123456");
    u3.setAccountNonExpired(true);
    u3.setAccountNonLocked(true);
    u3.setCredentialsNonExpired(true);
    u3.setEnabled(true);
    List<Role> rs3 = new ArrayList<>();
    rs3.add(role3);
    u3.setRoles(rs3);
    userAuthorityDao.save(u3);

    role4.setRoleNameZH("普通会员");
    role4.setRoleNameEN("ROLE_user");

    UserAuthority u4 = new UserAuthority();
    u4.setUsername("user@sjtu.edu.cn");
    u4.setPassword("123456");
    u4.setAccountNonExpired(true);
    u4.setAccountNonLocked(true);
    u4.setCredentialsNonExpired(true);
    u4.setEnabled(true);
    List<Role> rs4 = new ArrayList<>();

    rs4.add(role4);
    u4.setRoles(rs4);
    userAuthorityDao.save(u4);
  }
}
