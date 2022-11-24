package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.RoleDao;
import com.fundgroup.backend.entity.Role;
import com.fundgroup.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
    @Autowired
    RoleRepository roleRepository;

//    @Autowired
//    void setRoleRepository(RoleRepository roleRepository){
//      this.roleRepository = roleRepository;
//    }

    @Override
    public Role findOne(Integer role_id){
      return roleRepository.getById(role_id);
    }

    @Override
    public void save(Role role) {
      roleRepository.save(role);
    }
}
