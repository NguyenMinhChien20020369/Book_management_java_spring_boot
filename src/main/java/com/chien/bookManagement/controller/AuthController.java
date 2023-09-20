package com.chien.bookManagement.controller;

import com.chien.bookManagement.payload.request.LoginRequest;
import com.chien.bookManagement.payload.request.SignupRequest;
import com.chien.bookManagement.payload.response.SuccessResponse;
import com.chien.bookManagement.service.AuthService;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @Operation(summary = "Signin")
  @PostMapping("/signin")
  @JsonPropertyOrder(value = {"code", "message", "data"})
  public ResponseEntity<SuccessResponse> authenticateUser(
      @Valid @RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(authService.authenticateUser(loginRequest));
  }

  @Operation(summary = "Signup")
  @PostMapping("/signup")
  public ResponseEntity<Map<String, Object>> registerUser(
      @Valid @RequestBody SignupRequest signUpRequest) {
    return ResponseEntity.ok(authService.registerUser(signUpRequest));
  }
}
