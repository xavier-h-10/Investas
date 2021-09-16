package com.fundgroup.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "role")
//@Table(name = "role")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer roleId;
//  @Column(name = "role_nameEN")
  private String roleNameEN;
//  @Column(name = "role_nameZH")
  private String roleNameZH;


  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }


  public String getRoleNameEN() {
    return roleNameEN;
  }

  public void setRoleNameEN(String roleNameEN) {
    this.roleNameEN = roleNameEN;
  }


  public String getRoleNameZH() {
    return roleNameZH;
  }

  public void setRoleNameZH(String roleNameZH) {
    this.roleNameZH = roleNameZH;
  }
}
