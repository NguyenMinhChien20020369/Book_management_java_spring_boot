package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.BookCategoriesDto;
import com.chien.bookManagement.dto.BookCategoriesUpdateDto;
import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import com.chien.bookManagement.entity.BookCategories;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface BookCategoriesService extends
    GeneralService<BookCategoriesDto, BookCategories, BookCategoriesUpdateDto> {
  List<BookCategories> findByTitleOrAuthor(String name);
}
