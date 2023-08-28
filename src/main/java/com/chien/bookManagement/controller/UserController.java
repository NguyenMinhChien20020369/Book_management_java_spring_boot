package com.chien.bookManagement.controller;

import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import com.chien.bookManagement.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<UserDto> create(@RequestBody @Validated UserCreationDto userCreationDto) {
    return ResponseEntity.status(200).body(userService.create(userCreationDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Validated UserUpdateDto userUpdateDto) {
    return ResponseEntity.status(200).body(userService.update(id, userUpdateDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<UserDto> delete(@PathVariable Long id) {
    return ResponseEntity.status(200).body(userService.delete(id));
  }
  @GetMapping
  public ResponseEntity<Iterable<UserDto>> findAll() {
    return ResponseEntity.status(200).body(userService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> findById(@PathVariable Long id) {
    return ResponseEntity.status(200).body(userService.findById(id));
  }
  @GetMapping("/name/{name}")
  public ResponseEntity<Iterable<UserDto>> findByName(@PathVariable String name) {
    return ResponseEntity.status(200).body(userService.findByName(name));
  }
  @GetMapping("/phone/{phone}")
  public ResponseEntity<List<UserDto>> findByPhone(@PathVariable String phone) {
    return ResponseEntity.status(200).body(userService.findByPhone(phone));
  }
}
