package com.gxlpes.poc.auth.api.web.request;


public class AuthenticationRequest {

  private String email;
  String password;
  String code;

  public AuthenticationRequest(String email, String password, String code) {
    this.email = email;
    this.password = password;
    this.code = code;
  }

  public String getTfaCode() {
    return code;
  }

  public void setTfaCode(String code) {
    this.code = code;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
