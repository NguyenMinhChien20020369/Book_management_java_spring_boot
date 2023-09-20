package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.dto.OldBookAddDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.entity.BookCategories;
import java.util.List;
import java.util.Map;

public interface LibrarianService {

  Map<String, Object> addBook(BookCreationDto bookCreationDto);

  String addOldBook(OldBookAddDto oldBookAddDto);
}
