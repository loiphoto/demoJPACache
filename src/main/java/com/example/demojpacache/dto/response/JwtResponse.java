package com.example.demojpacache.dto.response;

import com.example.demojpacache.security.Role;
import lombok.Data;

@Data
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String email;
  private Role role;
  private final String refreshToken;

  public JwtResponse(String accessToken, Long id, String username, String email, Role role, String refreshToken) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.role = role;
    this.refreshToken = refreshToken;
  }

}
