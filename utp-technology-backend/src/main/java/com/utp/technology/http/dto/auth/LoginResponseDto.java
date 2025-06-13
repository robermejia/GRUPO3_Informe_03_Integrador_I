package com.utp.technology.http.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.utp.technology.http.dto.user.UserLoginDto;

import lombok.Data;

@Data
@JsonPropertyOrder({ "access_token", "user"})
public class LoginResponseDto {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("user")
  private UserLoginDto user;

}
