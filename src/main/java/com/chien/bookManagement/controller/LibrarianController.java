package com.chien.bookManagement.controller;

import com.chien.bookManagement.dto.ActivityHistoryDto;
import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.dto.IdsDto;
import com.chien.bookManagement.dto.OldBookAddDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.payload.response.SuccessResponse;
import com.chien.bookManagement.service.ActivityHistoryService;
import com.chien.bookManagement.service.LibrarianService;
import com.chien.bookManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
  public ResponseEntity<SuccessResponse> getAllNotEnabledAccount() {
    return ResponseEntity.status(200).body(userService.findByEnabled(false));
  }
  @Operation(summary = "Accept account")
  @PutMapping("/account/accept")
  public ResponseEntity<Map<String, Object>> acceptAccount(@RequestBody @Validated IdsDto idsDto) {
    return ResponseEntity.status(200).body(userService.acceptAccount(idsDto.getIds()));
  }
  @Operation(summary = "Add book")
  @PostMapping("/book")
  public ResponseEntity<Map<String, Object>> addBook(@RequestBody @Validated BookCreationDto bookCreationDto) {
    return ResponseEntity.status(200).body(librarianService.addBook(bookCreationDto));
  }
  @Operation(summary = "Add old book")
  @PostMapping("/oldBook")
  public ResponseEntity<SuccessResponse> addOldBook(@RequestBody @Validated OldBookAddDto oldBookAddDto) {
    return ResponseEntity.status(200).body(new SuccessResponse(librarianService.addOldBook(oldBookAddDto)));
  }

  @Operation(summary = "Disabled account")
  @PutMapping("/account/disabled")
  public ResponseEntity<Map<String, Object>> disabledAccount(@RequestBody @Validated IdsDto idsDto) {
    return ResponseEntity.status(200).body(userService.disabledAccount(idsDto.getIds()));
  }
//
  @Operation(summary = "View user activity")
  @GetMapping("/account/activity/{id:[0-9]{1,32}}")
  public ResponseEntity<SuccessResponse> viewUserActivity(@PathVariable Long id) {
    return ResponseEntity.status(200).body(activityHistoryService.viewUserActivity(id));
  }
}
