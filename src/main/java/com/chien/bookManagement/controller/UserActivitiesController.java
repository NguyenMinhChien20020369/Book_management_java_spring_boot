package com.chien.bookManagement.controller;

import com.chien.bookManagement.dto.BookBorrowingDto;
import com.chien.bookManagement.dto.BorrowingBooksDto;
import com.chien.bookManagement.dto.PaymentDto;
import com.chien.bookManagement.dto.ReturningBooksDto;
import com.chien.bookManagement.entity.BookBorrowing;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.payload.response.MessageResponse;
import com.chien.bookManagement.service.BookBorrowingService;
import com.chien.bookManagement.service.BookCategoriesService;
import com.chien.bookManagement.service.UserActivitiesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userActivities")
@PreAuthorize("hasRole('LIBRARIAN')")
public class UserActivitiesController {

  @Autowired
  private UserActivitiesService userActivitiesService;
  @Autowired
  private BookBorrowingService bookBorrowingService;

  @PostMapping
  public ResponseEntity<BookBorrowingDto> borrowingBooks(@RequestBody BorrowingBooksDto borrowingBooksDto) {
    return ResponseEntity.status(200).body(bookBorrowingService.create(borrowingBooksDto));
  }
  @PutMapping
  public ResponseEntity<List<BookBorrowingDto>> returningBooks(@RequestBody ReturningBooksDto returningBooksDto) {
    return ResponseEntity.status(200).body(userActivitiesService.returningBooks(returningBooksDto));
  }
  @PutMapping("/payment")
  public ResponseEntity<?> payment(@RequestBody PaymentDto paymentDto) {
    return ResponseEntity.status(200).body(new MessageResponse(userActivitiesService.payment(paymentDto)));
  }
}
