package com.chien.bookManagement.controller;

import com.chien.bookManagement.dto.BookBorrowingDto;
import com.chien.bookManagement.dto.BorrowingBooksDto;
import com.chien.bookManagement.dto.PaymentDto;
import com.chien.bookManagement.dto.ReturningBooksDto;
import com.chien.bookManagement.payload.response.SuccessResponse;
import com.chien.bookManagement.service.BookBorrowingService;
import com.chien.bookManagement.service.UserActivitiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userActivities")
@PreAuthorize("hasRole('LIBRARIAN')")
@SecurityRequirement(name = "javainuseapi")
public class UserActivitiesController {

  @Autowired
  private UserActivitiesService userActivitiesService;
  @Autowired
  private BookBorrowingService bookBorrowingService;
  @Operation(summary = "Borrowing books")
  @PostMapping
  public ResponseEntity<SuccessResponse> borrowingBooks(@RequestBody @Validated BorrowingBooksDto borrowingBooksDto) {
    return ResponseEntity.status(200).body(new SuccessResponse(bookBorrowingService.create(borrowingBooksDto)));
  }
  @Operation(summary = "Returning books")
  @PutMapping
  public ResponseEntity<SuccessResponse> returningBooks(@RequestBody @Validated ReturningBooksDto returningBooksDto) {
    return ResponseEntity.status(200).body(userActivitiesService.returningBooks(returningBooksDto));
  }
  @Operation(summary = "Payment")
  @PutMapping("/payment")
  public ResponseEntity<Map<String, Object>> payment(@RequestBody @Validated PaymentDto paymentDto) {
    return ResponseEntity.status(200).body(userActivitiesService.payment(paymentDto));
  }
}
