package com.fundgroup.backend.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "user_auth")
@DynamicUpdate
@DynamicInsert
@Data
// never pass real password to frontend
// @JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer",
// "fieldHandler",
// "password"})
public class UserAuthority implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  private String username;
  private String password;

  // 此处cascade改成了refresh,否则会报错:
  // nested exception is org.hibernate.PersistentObjectException:
  // uninitialized proxy passed to persist()
  // 20210815
  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
  private List<Role> roles;

  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;
  
  @CreationTimestamp
  @Column(name="created_at")
  private Timestamp createdAt;

  @UpdateTimestamp
  @Column(name="updated_at")
  private Timestamp updatedAt;


  // 是interface要求实现的函数，作用是返回role
  // role必须要求多对多，这里设定的角色名时英文
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    for (Role role : getRoles()) {
      authorities.add(new SimpleGrantedAuthority(role.getRoleNameEN()));
    }

    System.out.println(authorities.toString());

    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }


  @Override
  public String toString() {
    return "UserAuthority{" + "userId=" + userId + ", username='" + username + '\'' + ", password='" + password + '\''
        + ", roles=" + roles + ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
        + ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled + '}';
  }
}




//@Override
//  public String getUsername() {
//    return username;
//  }
//
//  @Override
//  public String getPassword() {
//    return password;
//  }
//
//  public List<Role> getRoles() {
//    return roles;
//  }

//  public Long getUserId() {
//    return userId;
//  }
//
//  public void setUserId(Long userId) {
//    this.userId = userId;
//  }
//
//  public void setUsername(String username) {
//    this.username = username;
//  }
//
//  public void setPassword(String password) {
//    this.password = password;
//  }
//
//  public void setRoles(List<Role> roles) {
//    this.roles = roles;
//  }
//
//  public void setAccountNonExpired(boolean accountNonExpired) {
//    this.accountNonExpired = accountNonExpired;
//  }
//
//  public void setAccountNonLocked(boolean accountNonLocked) {
//    this.accountNonLocked = accountNonLocked;
//  }
//
//  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
//    this.credentialsNonExpired = credentialsNonExpired;
//  }
//
//  public void setEnabled(boolean enabled) {
//    this.enabled = enabled;
//  }
