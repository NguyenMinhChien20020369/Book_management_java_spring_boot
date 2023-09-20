package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.entity.Book;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.exception.AppException;
import com.chien.bookManagement.payload.response.SuccessResponse;
import com.chien.bookManagement.repository.BookRepository;
import com.chien.bookManagement.service.BookService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private ModelMapper mapper;

  @Override
  public Book create(BookCategories bookCategories) {
    Book book = new Book(bookCategories);
    return bookRepository.save(mapper.map(book, Book.class));
  }

  @Override
  public SuccessResponse update(Book BookInput) {
    Book fromDB = bookRepository.findById(BookInput.getId()).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, 44, "Error: Does not exist! Book not found!");
    }
    fromDB.setId(BookInput.getId());
    fromDB.setId(BookInput.getId());
    fromDB.setId(BookInput.getId());
    fromDB.setId(BookInput.getId());
    return new SuccessResponse(bookRepository.save(fromDB));
  }

  @Override
  public Map<String, Object> delete(Long id) {
    Book fromDB = bookRepository.findById(id).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, 44, "Error: Does not exist! Book not found!");
    }
    bookRepository.deleteById(id);
    final Map<String, Object> body = new HashMap<>();
    body.put("code", 0);
    body.put("message", "Successfully deleted!");
    return body;
  }

  @Override
  public SuccessResponse findById(Long id) {
    Book book = bookRepository.findById(id).orElse(null);
    if (book == null) {
      throw new AppException(404, 44, "Error: Does not exist! Book not found!");
    } else {
      return new SuccessResponse(book);
    }
  }

  @Override
  public SuccessResponse findAll() {
    List<Book> userList = bookRepository.findAll();
    if (userList.isEmpty()) {
      throw new AppException(404, 44, "Error: Does not exist! No book has been created yet!");
    }
    return new SuccessResponse(userList);
  }
}
