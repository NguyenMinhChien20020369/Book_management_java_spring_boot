package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.dto.BookBorrowingDto;
import com.chien.bookManagement.dto.BorrowingBooksDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.entity.Book;
import com.chien.bookManagement.entity.BookBorrowing;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.entity.User;
import com.chien.bookManagement.exception.AppException;
import com.chien.bookManagement.repository.BookBorrowingRepository;
import com.chien.bookManagement.repository.BookCategoriesRepository;
import com.chien.bookManagement.repository.BookRepository;
import com.chien.bookManagement.repository.UserRepository;
import com.chien.bookManagement.service.BookBorrowingService;
import com.chien.bookManagement.service.BookBorrowingService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookBorrowingServiceImpl implements BookBorrowingService {

  @Autowired
  private BookBorrowingRepository BookBorrowingRepository;
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private BookCategoriesRepository bookCategoriesRepository;
  @Autowired
  private ModelMapper mapper;

  @Override
  public BookBorrowingDto create(BorrowingBooksDto borrowingBooksDto) {
    Book book = bookRepository.findById(borrowingBooksDto.getBookId()).orElse(null);
    if (book == null) {
      throw new AppException(404, "Book not found");
    } else if (Objects.equals(book.getStatus(), "Currently being borrowed")) {
      throw new AppException(400, "Currently being borrowed");
    }
    book.setStatus("Currently being borrowed");
    bookRepository.save(book);
    BookCategories bookCategories = book.getBookCategories();
    bookCategories.setAvailableQuantity(bookCategories.getAvailableQuantity() - 1);
    bookCategoriesRepository.save(bookCategories);
    User user = userRepository.findById(borrowingBooksDto.getUserId()).orElse(null);
    if (user == null) {
      throw new AppException(404, "User not found");
    }
    BookBorrowing bookBorrowing = new BookBorrowing(user, book);
    return mapper.map(BookBorrowingRepository.save(mapper.map(bookBorrowing, BookBorrowing.class)), BookBorrowingDto.class);
  }

  @Override
  public BookBorrowingDto update(Long id, BookBorrowing BookBorrowingInput) {
    BookBorrowing fromDB = BookBorrowingRepository.findById(id).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, "User not found");
    }
    fromDB.setId(BookBorrowingInput.getId());
    return mapper.map(BookBorrowingRepository.save(fromDB), BookBorrowingDto.class);
  }

  @Override
  public BookBorrowingDto delete(Long id) {
    BookBorrowing fromDB = BookBorrowingRepository.findById(id).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, "User not found");
    }
    BookBorrowingRepository.deleteById(id);
    return mapper.map(fromDB, BookBorrowingDto.class);
  }

  @Override
  public BookBorrowingDto findById(Long id) {
    BookBorrowing user = BookBorrowingRepository.findById(id).orElse(null);
    if (user == null) {
      throw new AppException(404, "User not found");
    } else {
      return mapper.map(user, BookBorrowingDto.class);
    }
  }

  @Override
  public Iterable<BookBorrowingDto> findAll() {
    List<BookBorrowing> userList = BookBorrowingRepository.findAll();
    if (userList.isEmpty()) {
      throw new AppException(404, "No user has been created yet!");
    }
    return userList.stream()
        .map(bookBorrowing -> mapper.map(bookBorrowing, BookBorrowingDto.class)).collect(
            Collectors.toList());
  }
}
