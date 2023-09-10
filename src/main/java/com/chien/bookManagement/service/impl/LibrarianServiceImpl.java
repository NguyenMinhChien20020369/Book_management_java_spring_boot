package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.service.BookCategoriesService;
import com.chien.bookManagement.service.BookService;
import com.chien.bookManagement.service.LibrarianService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibrarianServiceImpl implements LibrarianService {

  @Autowired
  private BookCategoriesService bookCategoriesService;

  @Autowired
  private BookService bookService;

  @Autowired
  private ModelMapper mapper;

  @Override
  public String addBook(BookCreationDto bookCreationDto) {
    BookCategories bookCategories = bookCategoriesService.create(
        mapper.map(bookCreationDto, BookCategories.class));
    for (int i = 0; i < bookCreationDto.getAmount(); i++) {
      bookService.create(bookCategories);
    }
    return "Successfully!";
  }
}
