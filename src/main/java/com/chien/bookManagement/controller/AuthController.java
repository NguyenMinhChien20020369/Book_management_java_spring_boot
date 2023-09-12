package com.chien.bookManagement.controller;

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
import com.chien.bookManagement.payload.response.MessageResponse;
import com.chien.bookManagement.repository.ActivityHistoryRepository;
import com.chien.bookManagement.repository.RoleRepository;
import com.chien.bookManagement.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private ActivityHistoryRepository activityHistoryRepository;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private JwtUtils jwtUtils;

  @Operation(summary = "Signin")
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    User fromDB = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
    ActivityHistory activity = new ActivityHistory("Signin", LocalDateTime.now(), fromDB);
    if (fromDB == null) {
      throw new AppException(404, "User not found!");
    } else if (!fromDB.isEnabled()) {
      activityHistoryRepository.save(activity);
      throw new AppException(407,
          "Your account has not been approved by the librarian yet, please wait!");
    } else if (fromDB.getDisabled()) {
      activityHistoryRepository.save(activity);
      throw new AppException(407,
          "Your account has been disabled by the librarian because you violated the rules!");
    }
    activityHistoryRepository.save(activity);

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        roles));
  }

  @Operation(summary = "Signup")
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
        encoder.encode(signUpRequest.getPassword()),
        signUpRequest.getEmail());

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "lib":
            Role libRole = roleRepository.findByName(ERole.ROLE_LIBRARIAN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(libRole);

            break;
          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);
    ActivityHistory activity = new ActivityHistory("Signup", LocalDateTime.now(), user);
    activityHistoryRepository.save(activity);

    return ResponseEntity.ok(new MessageResponse(
        "User registered successfully! Please wait for the librarian to approve your account!"));
  }
}
