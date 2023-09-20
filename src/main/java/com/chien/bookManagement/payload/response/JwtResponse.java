package com.chien.bookManagement.payload.response;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class JwtResponse {

  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String email;
  private List<Map<String, Object>> roles;

  public JwtResponse(String accessToken, Long id, String username, String email,
      List<Map<String, Object>> roles) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }
}
