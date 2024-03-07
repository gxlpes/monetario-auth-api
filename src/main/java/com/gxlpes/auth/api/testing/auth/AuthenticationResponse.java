package com.gxlpes.auth.api.testing.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {

  public AuthenticationResponse(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }


  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;


}
