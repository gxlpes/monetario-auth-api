package com.gxlpes.poc.auth.api.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public String id;

  @Column(unique = true)
  public String token;

  public String tokenType;

  @CreationTimestamp
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  public boolean revoked;

  public boolean expired;

  public boolean logout;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public User user;

  public Token() {
  }

  public Token(String id, String token, String tokenType, boolean revoked,
               boolean expired, User user) {
    this.id = id;
    this.token = token;
    this.tokenType = tokenType;
    this.revoked = revoked;
    this.expired = expired;
    this.user = user;
  }

  public boolean isRevoked() {
    return revoked;
  }

  public boolean isExpired() {
    return expired;
  }

  public String getId() {
    return id;
  }

  public void setLogout(boolean logout) {
    this.logout = logout;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public void setRevoked(boolean revoked) {
    this.revoked = revoked;
  }

  public void setExpired(boolean expired) {
    this.expired = expired;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
