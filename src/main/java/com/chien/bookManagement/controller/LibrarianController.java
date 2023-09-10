package com.chien.bookManagement.controller;

import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import com.chien.bookManagement.payload.response.MessageResponse;
import com.chien.bookManagement.service.LibrarianService;
import com.chien.bookManagement.service.UserService;
import java.util.Collection;
import java.util.List;
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
@RequestMapping("/api/lib")
@PreAuthorize("hasRole('LIBRARIAN')")
public class LibrarianController {
  @Autowired
  private UserService userService;

  @Autowired
  private LibrarianService librarianService;

  @GetMapping("/home")
  public String librarianAccess() {
    return "Librarian Board.";
  }

  @GetMapping("/account")
  public ResponseEntity<Iterable<UserDto>> getAllNotEnabledAccount() {
    return ResponseEntity.status(200).body(userService.findByEnabled(false));
  }

  @PutMapping("/account/accept")
  public ResponseEntity<MessageResponse> acceptAccount(@RequestBody Collection<Long> ids) {
    return ResponseEntity.status(200).body(new MessageResponse(userService.acceptAccount(ids)));
  }

  @DeleteMapping("/account/reject")
  public ResponseEntity<MessageResponse> rejectAccount(@RequestBody Collection<Long> ids) {
    return ResponseEntity.status(200).body(new MessageResponse(userService.rejectAccount(ids)));
  }

  @PostMapping("/book")
  public ResponseEntity<MessageResponse> addBook(@RequestBody BookCreationDto bookCreationDto) {
    return ResponseEntity.status(200).body(new MessageResponse(librarianService.addBook(bookCreationDto)));
  }
}
