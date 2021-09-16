package com.fundgroup.backend.dto;

import com.fundgroup.backend.entity.Role;
import java.util.List;
import lombok.Data;

@Data
public class FullUserInfo {
  private Long userId;
  private String username;

  private boolean accountNonExpired;
  private String nickname;
  private Integer riskLevel;
  private String email;
  private Integer status;

  private String avatarUrl;
  private String introduction;
  private List<Role> roles;

  public FullUserInfo() {
  }

  public FullUserInfo(Long userId, String username, boolean accountNonExpired,
      String nickname, Integer riskLevel, String email, Integer status, String avatarUrl,
      String introduction, List<Role> roles) {
    this.userId = userId;
    this.username = username;
    this.accountNonExpired = accountNonExpired;
    this.nickname = nickname;
    this.riskLevel = riskLevel;
    this.email = email;
    this.status = status;
    this.avatarUrl = avatarUrl;
    this.introduction = introduction;
    this.roles = roles;
  }
}
