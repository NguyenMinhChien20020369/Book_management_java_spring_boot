package com.chien.bookManagement.controller;

import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import com.chien.bookManagement.payload.response.MessageResponse;
import com.chien.bookManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.MissingResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "javainuseapi")
public class UserController {

  @Autowired
  private UserService userService;

  @PreAuthorize("hasRole('A')")
  @PostMapping
  public ResponseEntity<UserDto> create(@RequestBody @Validated UserCreationDto userCreationDto) {
    return ResponseEntity.status(200).body(userService.create(userCreationDto));
  }

  @Operation(summary = "Update user")
  @PutMapping
  public ResponseEntity<UserDto> update(@RequestBody @Validated UserUpdateDto userUpdateDto) {
    return ResponseEntity.status(200).body(userService.update(userUpdateDto));
  }

  @Operation(summary = "Delete user")
  @DeleteMapping("/{id}")
  public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
    return ResponseEntity.status(200).body(userService.delete(id));
  }
  @PreAuthorize("hasRole('LIBRARIAN')")
  @Operation(summary = "Get all user")
  @GetMapping
  public ResponseEntity<Iterable<UserDto>> findAll() {
    return ResponseEntity.status(200).body(userService.findAll());
  }
  @PreAuthorize("hasRole('LIBRARIAN')")
  @Operation(summary = "Get user by id")
  @GetMapping("/{id}")
  public ResponseEntity<UserDto> findById(@PathVariable Long id) {
    return ResponseEntity.status(200).body(userService.findById(id));
  }
  @PreAuthorize("hasRole('LIBRARIAN')")
  @Operation(summary = "Get user by name")
  @GetMapping("/name/{name}")
  public ResponseEntity<Iterable<UserDto>> findByName(@PathVariable String name) {
    return ResponseEntity.status(200).body(userService.findByName(name));
  }
  @PreAuthorize("hasRole('LIBRARIAN')")
  @Operation(summary = "Get user by phone")
  @GetMapping("/phone/{phone}")
  public ResponseEntity<List<UserDto>> findByPhone(@PathVariable String phone) {
    return ResponseEntity.status(200).body(userService.findByPhone(phone));
  }
}
