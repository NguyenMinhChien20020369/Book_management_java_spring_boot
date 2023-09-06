package com.chien.bookManagement.controller;

import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import com.chien.bookManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lib")
@PreAuthorize("hasRole('LIBRARIAN')")
public class LibrarianController {
  @Autowired
  private UserService userService;

  @GetMapping("/home")
  public String librarianAccess() {
    return "Librarian Board.";
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Validated UserUpdateDto userUpdateDto) {
    return ResponseEntity.status(200).body(userService.update(id, userUpdateDto));
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Iterable<UserDto>> findByName(@PathVariable String name) {
    return ResponseEntity.status(200).body(userService.findByName(name));
  }
}
