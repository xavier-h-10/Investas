package com.fundgroup.backend.dao;

import com.fundgroup.backend.entity.Role;

public interface RoleDao {
  Role findOne(Integer role_id);

  void save(Role role);
}
