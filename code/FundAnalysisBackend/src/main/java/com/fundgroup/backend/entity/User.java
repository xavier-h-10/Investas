package com.fundgroup.backend.entity;

import com.sun.istack.NotNull;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "user")
public class User {
  @Id
  @NotNull
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name="user_id")
  private Long userId;

  @NotNull
  @Column(name="nickname")
  private String nickname;

  @NotNull
  @Column(name="risk_level")
  private Integer riskLevel;

  @NotNull
  @Column(name="email")
  private String email;

  @NotNull
  @Column(name="status")
  private Integer status;

  @NotNull
  @ColumnDefault("")
  @Column(name="avatar_url")
  private String avatarUrl = "";

  @Column(name="introduction")
  @ColumnDefault("")
  private String introduction = "";

//  public void setAvatarUrl(String url) {
//    this.avatarUrl=url;
//  }

  @CreationTimestamp
  @Column(name = "created_at")
  private Timestamp createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Timestamp updatedAt;
}




//  public User() {
//  }
//
//  public User(Long userId) {
//    this.userId = userId;
//  }
//@Id
//  @Column(name = "user_id")
//  public Long getUserId() {
//    return userId;
//  }
//
//  public void setUserId(Long userId) {
//    this.userId = userId;
//  }
//
//  @Column(name = "nickname")
//  public String getNickname() {
//    return nickname;
//  }
//
//  public void setNickname(String nickname) {
//    this.nickname = nickname;
//  }
//
//  @Column(name = "status")
//  public Integer getStatus() {
//    return status;
//  }
//
//  public void setStatus(Integer status) {
//    this.status = status;
//  }
//
//  @Column(name = "risk_level")
//  public int getRiskLevel() {
//    return riskLevel;
//  }
//
//  public void setRiskLevel(int riskLevel) {
//    this.riskLevel = riskLevel;
//  }
//
//  @Column(name = "email")
//  public String getEmail() {
//    return email;
//  }
//
//  public void setEmail(String email) {
//    this.email = email;
//  }

//  @Override
//  public String toString() {
//    return "User{" +
//        "userId=" + userId +
//        ", nickname='" + nickname + '\'' +
//        ", status=" + status +
//        '}';
//  }
