package com.chien.bookManagement.service;

import com.chien.bookManagement.payload.request.LoginRequest;
import com.chien.bookManagement.payload.request.SignupRequest;
import com.chien.bookManagement.payload.response.SuccessResponse;
import java.util.Map;

public interface AuthService {

  SuccessResponse authenticateUser(LoginRequest loginRequest);

  Map<String, Object> registerUser(SignupRequest signUpRequest);
}
