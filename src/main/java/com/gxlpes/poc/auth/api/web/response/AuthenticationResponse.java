package com.gxlpes.poc.auth.api.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {

  public AuthenticationResponse(String accessToken, String refreshToken, String secretImageUri) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.secretImageUri = secretImageUri;
  }

  public AuthenticationResponse(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public AuthenticationResponse(String accessToken) {
    this.accessToken = accessToken;
  }

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
  @JsonProperty("secret_image_uri")
  private String secretImageUri;

}
