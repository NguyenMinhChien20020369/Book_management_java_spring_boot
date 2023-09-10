package com.chien.bookManagement.controller;

import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.service.BookCategoriesService;
import com.chien.bookManagement.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookCategories")
public class BookCategoriesController {
  @Autowired
  private BookCategoriesService bookCategoriesService;

  @PreAuthorize("hasRole('A')")
  @PostMapping
  public ResponseEntity<BookCategories> create(@RequestBody BookCategories bookCategories) {
    return ResponseEntity.status(200).body(bookCategoriesService.create(bookCategories));
  }

  @PreAuthorize("hasRole('LIBRARIAN') or hasRole('USER')")
  @GetMapping("/{name}")
  public ResponseEntity<List<BookCategories>> findByTitleOrAuthor(@PathVariable String name) {
    return ResponseEntity.status(200).body(bookCategoriesService.findByTitleOrAuthor(name));
  }
}
