package com.chien.bookManagement.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('LIBRARIAN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/lib")
  @PreAuthorize("hasRole('LIBRARIAN')")
  public String librarianAccess() {
    return "Librarian Board.";
  }
}
