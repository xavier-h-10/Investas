package com.fundgroup.backend.service;

import com.fundgroup.backend.dao.RoleDao;
import com.fundgroup.backend.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 由spring security 支持

public interface RoleService {

  public Role getRoleById(Integer role_id);
}
