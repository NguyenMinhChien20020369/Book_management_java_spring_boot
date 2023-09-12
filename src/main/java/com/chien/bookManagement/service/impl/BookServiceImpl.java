package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.entity.Book;
import com.chien.bookManagement.entity.Book;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.exception.AppException;
import com.chien.bookManagement.payload.response.MessageResponse;
import com.chien.bookManagement.repository.BookRepository;
import com.chien.bookManagement.repository.BookRepository;
import com.chien.bookManagement.service.BookService;
import com.chien.bookManagement.service.BookService;
import java.util.List;
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
  public Book update(Book BookInput) {
    Book fromDB = bookRepository.findById(BookInput.getId()).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, "User not found");
    }
    fromDB.setId(BookInput.getId());
    fromDB.setId(BookInput.getId());
    fromDB.setId(BookInput.getId());
    fromDB.setId(BookInput.getId());
    return bookRepository.save(fromDB);
  }

  @Override
  public MessageResponse delete(Long id) {
    Book fromDB = bookRepository.findById(id).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, "User not found");
    }
    bookRepository.deleteById(id);
    return new MessageResponse("Successfully deleted!");
  }

  @Override
  public Book findById(Long id) {
    Book book = bookRepository.findById(id).orElse(null);
    if (book == null) {
      throw new AppException(404, "Book not found");
    } else {
      return book;
    }
  }

  @Override
  public Iterable<Book> findAll() {
    List<Book> userList = bookRepository.findAll();
    if (userList.isEmpty()) {
      throw new AppException(404, "No user has been created yet!");
    }
    return userList;
  }
}
