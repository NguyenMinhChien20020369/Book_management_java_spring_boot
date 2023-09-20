package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.dto.OldBookAddDto;
import com.chien.bookManagement.entity.Book;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.exception.AppException;
import com.chien.bookManagement.repository.BookCategoriesRepository;
import com.chien.bookManagement.service.BookCategoriesService;
import com.chien.bookManagement.service.BookService;
import com.chien.bookManagement.service.LibrarianService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private BookCategoriesRepository bookCategoriesRepository;

  @Autowired
  private ModelMapper mapper;

  @Override
  public Map<String, Object> addBook(BookCreationDto bookCreationDto) {
    if (bookCategoriesRepository.existsByTitleAndAuthor(bookCreationDto.getTitle(), bookCreationDto.getAuthor())) {
      throw new AppException(400, 40, "Error: This book category already exists in the system!");
    }
    BookCategories bookCategoriesNew = new BookCategories(
        mapper.map(bookCreationDto, BookCategories.class));
    BookCategories bookCategories = bookCategoriesRepository.save(
        mapper.map(bookCategoriesNew, BookCategories.class));
    for (int i = 0; i < bookCreationDto.getAmount(); i++) {
      bookService.create(bookCategories);
    }
    final Map<String, Object> body = new HashMap<>();
    body.put("code", 0);
    body.put("message", "Successfully!");
    return body;
  }

  @Override
  public String addOldBook(OldBookAddDto oldBookAddDto) {
    BookCategories bookCategories = bookCategoriesRepository.findById(oldBookAddDto.getId())
        .orElseThrow(() -> new AppException(404, 44, "Error: Does not exist! Book categories not found!"));
    bookCategories.setAmount(bookCategories.getAmount() + oldBookAddDto.getAddQuantity());
    bookCategories.setAvailableQuantity(bookCategories.getAvailableQuantity() + oldBookAddDto.getAddQuantity());
    bookCategoriesRepository.save(bookCategories);
    List<Long> ids = new ArrayList<>();
    for (int i = 0; i < oldBookAddDto.getAddQuantity(); i++) {
      ids.add(bookService.create(bookCategories).getId());
    }
    return "Successfully! List of newly created IDs: " + ids.toString();
  }
}
