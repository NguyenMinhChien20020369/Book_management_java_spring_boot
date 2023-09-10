package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.entity.BookCategories;
import java.util.List;

public interface LibrarianService {
  String addBook(BookCreationDto bookCreationDto);
}
