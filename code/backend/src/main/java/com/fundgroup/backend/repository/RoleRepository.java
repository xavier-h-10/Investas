package com.fundgroup.backend.repository;

import com.fundgroup.backend.entity.FundBasicInfo;
import com.fundgroup.backend.entity.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {


}
