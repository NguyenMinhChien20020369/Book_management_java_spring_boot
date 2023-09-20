package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.entity.ActivityHistory;
import com.chien.bookManagement.entity.ERole;
import com.chien.bookManagement.entity.Role;
import com.chien.bookManagement.entity.User;
import com.chien.bookManagement.entity.UserDetailsImpl;
import com.chien.bookManagement.exception.AppException;
import com.chien.bookManagement.jwt.JwtUtils;
import com.chien.bookManagement.payload.request.LoginRequest;
import com.chien.bookManagement.payload.request.SignupRequest;
import com.chien.bookManagement.payload.response.JwtResponse;
import com.chien.bookManagement.payload.response.SuccessResponse;
import com.chien.bookManagement.repository.ActivityHistoryRepository;
import com.chien.bookManagement.repository.RoleRepository;
import com.chien.bookManagement.repository.UserRepository;
import com.chien.bookManagement.service.AuthService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ActivityHistoryRepository activityHistoryRepository;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private PasswordEncoder encoder;
  @Autowired
  private RoleRepository roleRepository;


  @Override
  public SuccessResponse authenticateUser(LoginRequest loginRequest) {
    User fromDB = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
    ActivityHistory activity = new ActivityHistory("Signin", LocalDateTime.now(), fromDB);
    if (fromDB == null) {
      throw new AppException(404, 44, "Error: Does not exist! User not found!");
    } else if (!fromDB.isEnabled()) {
      activityHistoryRepository.save(activity);
      throw new AppException(403, 43,
          "Your account has not been approved by the librarian yet, please wait!");
    } else if (fromDB.getDisabled() != null) {
      if (fromDB.getDisabled()) {
        activityHistoryRepository.save(activity);
        throw new AppException(403, 43,
            "Your account has been disabled by the librarian because you violated the rules!");
      }
    }
    activityHistoryRepository.save(activity);

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<Map<String, Object>> roles = userDetails.getAuthorities().stream()
        .map(grantedAuthority -> {
          final Map<String, Object> body = new HashMap<>();
          body.put("name", grantedAuthority.getAuthority());
          return body;
        })
        .collect(Collectors.toList());
    return new SuccessResponse(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        roles));
  }

  @Override
  public Map<String, Object> registerUser(SignupRequest signUpRequest) {
    if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
      throw new AppException(400, 40, "Error: Username is already taken!");
    }

    if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
      throw new AppException(400, 40, "Error: Email is already in use!");
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
        encoder.encode(signUpRequest.getPassword()),
        signUpRequest.getEmail());

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        if ("lib".equals(role)) {
          Role libRole = roleRepository.findByName(ERole.ROLE_LIBRARIAN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
          roles.add(libRole);
        } else {
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found!"));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);
    ActivityHistory activity = new ActivityHistory("Signup", LocalDateTime.now(), user);
    activityHistoryRepository.save(activity);

    final Map<String, Object> body = new HashMap<>();
    body.put("code", 0);
    body.put("message",
        "User registered successfully! Please wait for the librarian to approve your account!");

    return body;
  }
}
