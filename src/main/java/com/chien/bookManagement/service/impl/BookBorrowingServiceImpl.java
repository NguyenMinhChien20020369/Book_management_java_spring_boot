package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.dto.BookBorrowingDto;
import com.chien.bookManagement.dto.BorrowingBooksDto;
import com.chien.bookManagement.entity.ActivityHistory;
import com.chien.bookManagement.entity.Book;
import com.chien.bookManagement.entity.BookBorrowing;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.entity.User;
import com.chien.bookManagement.exception.AppException;
import com.chien.bookManagement.payload.response.SuccessResponse;
import com.chien.bookManagement.repository.ActivityHistoryRepository;
import com.chien.bookManagement.repository.BookBorrowingRepository;
import com.chien.bookManagement.repository.BookCategoriesRepository;
import com.chien.bookManagement.repository.BookRepository;
import com.chien.bookManagement.repository.UserRepository;
import com.chien.bookManagement.service.BookBorrowingService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private ActivityHistoryRepository activityHistoryRepository;
  @Autowired
  private ModelMapper mapper;

  @Override
  public BookBorrowingDto create(BorrowingBooksDto borrowingBooksDto) {
    User user = userRepository.findById(borrowingBooksDto.getUserId()).orElse(null);
    if (user == null) {
      throw new AppException(404, 44, "Error: Does not exist! User not found!");
    }

    ActivityHistory activity = new ActivityHistory("Borrowing books", LocalDateTime.now(), user);
    activityHistoryRepository.save(activity);

    Book book = bookRepository.findById(borrowingBooksDto.getBookId()).orElse(null);
    if (book == null) {
      throw new AppException(404, 44, "Error: Does not exist! Book not found!");
    } else if (Objects.equals(book.getStatus(), "Currently being borrowed")) {
      throw new AppException(400, 3, "Currently being borrowed!");
    }
    book.setStatus("Currently being borrowed");
    bookRepository.save(book);
    BookCategories bookCategories = book.getBookCategories();
    bookCategories.setAvailableQuantity(bookCategories.getAvailableQuantity() - 1);
    bookCategoriesRepository.save(bookCategories);

    BookBorrowing bookBorrowing = new BookBorrowing(user, book);
    return mapper.map(BookBorrowingRepository.save(mapper.map(bookBorrowing, BookBorrowing.class)), BookBorrowingDto.class);
  }

  @Override
  public SuccessResponse update(BookBorrowing BookBorrowingInput) {
    BookBorrowing fromDB = BookBorrowingRepository.findById(BookBorrowingInput.getId()).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, 44, "Error: Does not exist! Book borrowing not found!");
    }
    fromDB.setId(BookBorrowingInput.getId());
    return new SuccessResponse(mapper.map(BookBorrowingRepository.save(fromDB), BookBorrowingDto.class));
  }

  @Override
  public Map<String, Object> delete(Long id) {
    BookBorrowing fromDB = BookBorrowingRepository.findById(id).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, 44, "Error: Does not exist! Book borrowing not found!");
    }
    BookBorrowingRepository.deleteById(id);
    final Map<String, Object> body = new HashMap<>();
    body.put("code", 0);
    body.put("message", "Successfully deleted!");
    return body;
  }

  @Override
  public SuccessResponse findById(Long id) {
    BookBorrowing bookBorrowing = BookBorrowingRepository.findById(id).orElse(null);
    if (bookBorrowing == null) {
      throw new AppException(404, 44, "Error: Does not exist! Book borrowing not found!");
    } else {
      return new SuccessResponse(mapper.map(bookBorrowing, BookBorrowingDto.class));
    }
  }

  @Override
  public SuccessResponse findAll() {
    List<BookBorrowing> userList = BookBorrowingRepository.findAll();
    if (userList.isEmpty()) {
      throw new AppException(404, 44, "Error: Does not exist! No user has been created yet!");
    }
    return new SuccessResponse(userList.stream()
        .map(bookBorrowing -> mapper.map(bookBorrowing, BookBorrowingDto.class)).collect(
            Collectors.toList()));
  }
}
