package com.chien.bookManagement.controller;

import com.chien.bookManagement.dto.BookCategoriesDto;
import com.chien.bookManagement.dto.BookCategoriesUpdateDto;
import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.payload.response.MessageResponse;
import com.chien.bookManagement.service.BookCategoriesService;
import com.chien.bookManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/api/bookCategories")
@SecurityRequirement(name = "javainuseapi")
public class BookCategoriesController {
  @Autowired
  private BookCategoriesService bookCategoriesService;

  @PreAuthorize("hasRole('A')")
  @PostMapping
  public ResponseEntity<BookCategoriesDto> create(@RequestBody BookCategories bookCategories) {
    return ResponseEntity.status(200).body(bookCategoriesService.create(bookCategories));
  }
  @Operation(summary = "Find by title or author")
  @PreAuthorize("hasRole('LIBRARIAN') or hasRole('USER')")
  @GetMapping("/{name}")
  public ResponseEntity<List<BookCategories>> findByTitleOrAuthor(@PathVariable String name) {
    return ResponseEntity.status(200).body(bookCategoriesService.findByTitleOrAuthor(name));
  }

  @Operation(summary = "Update book")
  @PreAuthorize("hasRole('LIBRARIAN')")
  @PutMapping
  public ResponseEntity<BookCategoriesDto> updateBook(@RequestBody @Validated BookCategoriesUpdateDto bookCategoriesUpdateDto) {
    return ResponseEntity.status(200).body(bookCategoriesService.update(bookCategoriesUpdateDto));
  }

  @Operation(summary = "Delete book")
  @PreAuthorize("hasRole('LIBRARIAN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<MessageResponse> deleteBook(@PathVariable Long id) {
    return ResponseEntity.status(200).body(bookCategoriesService.delete(id));
  }
}
