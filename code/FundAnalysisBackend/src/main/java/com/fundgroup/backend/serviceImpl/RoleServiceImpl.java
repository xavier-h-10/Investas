package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.dao.RoleDao;
import com.fundgroup.backend.entity.Role;
import com.fundgroup.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired
  RoleDao roleDao;

//  @Autowired
//  void setRoleDao(RoleDao roleDao){
//    this.roleDao = roleDao;
//  }

  @Override
  public Role getRoleById(Integer role_id) {
    return roleDao.findOne(role_id);
  }
}
