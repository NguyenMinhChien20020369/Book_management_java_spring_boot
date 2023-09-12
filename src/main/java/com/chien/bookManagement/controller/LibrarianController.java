package com.chien.bookManagement.controller;

import com.chien.bookManagement.dto.ActivityHistoryDto;
import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import com.chien.bookManagement.entity.ActivityHistory;
import com.chien.bookManagement.payload.response.MessageResponse;
import com.chien.bookManagement.service.ActivityHistoryService;
import com.chien.bookManagement.service.LibrarianService;
import com.chien.bookManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
@SecurityRequirement(name = "javainuseapi")
public class LibrarianController {
  @Autowired
  private UserService userService;

  @Autowired
  private LibrarianService librarianService;
  @Autowired
  private ActivityHistoryService activityHistoryService;

  @GetMapping("/home")
  public String librarianAccess() {
    return "Librarian Board.";
  }
  @Operation(summary = "Get all not enabled account")
  @GetMapping("/account")
  public ResponseEntity<Iterable<UserDto>> getAllNotEnabledAccount() {
    return ResponseEntity.status(200).body(userService.findByEnabled(false));
  }
  @Operation(summary = "Accept account")
  @PutMapping("/account/accept")
  public ResponseEntity<MessageResponse> acceptAccount(@RequestBody Collection<Long> ids) {
    return ResponseEntity.status(200).body(new MessageResponse(userService.acceptAccount(ids)));
  }
  @Operation(summary = "Reject account")
  @DeleteMapping("/account/reject")
  public ResponseEntity<MessageResponse> rejectAccount(@RequestBody Collection<Long> ids) {
    return ResponseEntity.status(200).body(new MessageResponse(userService.rejectAccount(ids)));
  }
  @Operation(summary = "Add book")
  @PostMapping("/book")
  public ResponseEntity<MessageResponse> addBook(@RequestBody @Validated BookCreationDto bookCreationDto) {
    return ResponseEntity.status(200).body(new MessageResponse(librarianService.addBook(bookCreationDto)));
  }

  @Operation(summary = "Disabled account")
  @PutMapping("/account/disabled")
  public ResponseEntity<String> disabledAccount(@RequestBody Collection<Long> ids) {
    return ResponseEntity.status(200).body(userService.disabledAccount(ids));
  }

  @Operation(summary = "View user activity")
  @GetMapping("/account/activity/{id}")
  public ResponseEntity<List<ActivityHistoryDto>> viewUserActivity(@PathVariable Long id) {
    return ResponseEntity.status(200).body(activityHistoryService.viewUserActivity(id));
  }
}
