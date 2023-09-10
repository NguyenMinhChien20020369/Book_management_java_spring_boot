package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import com.chien.bookManagement.entity.BookCategories;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface BookCategoriesService extends
    com.chien.demoPerson.service.GeneralService<BookCategories, BookCategories, BookCategories> {
  List<BookCategories> findByTitleOrAuthor(String name);
}
