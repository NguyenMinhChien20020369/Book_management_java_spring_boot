package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.dto.BookBorrowingDto;
import com.chien.bookManagement.dto.BorrowingBooksDto;
import com.chien.bookManagement.dto.PaymentDto;
import com.chien.bookManagement.dto.ReturningBooksDto;
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
import com.chien.bookManagement.service.BookCategoriesService;
import com.chien.bookManagement.service.UserActivitiesService;
import java.time.Duration;
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
public class UserActivitiesServiceImpl implements UserActivitiesService {

  @Autowired
  private BookBorrowingRepository bookBorrowingRepository;
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private BookCategoriesRepository bookCategoriesRepository;

  @Autowired
  private ActivityHistoryRepository activityHistoryRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ModelMapper mapper;

  @Override
  public SuccessResponse returningBooks(ReturningBooksDto returningBooksDto) {
    LocalDateTime now = LocalDateTime.now();
    List<BookBorrowing> bookBorrowingList = bookBorrowingRepository.findBooksToBeReturnedByBookIds(
        returningBooksDto.getBookIds()).orElseThrow(
        () -> new AppException(404, 44, "Error: Does not exist! Book borrowing not found!"));
    if (bookBorrowingList.isEmpty()) {
      throw new AppException(400, 4, "You can only return books that haven't been returned yet!");
    }

    ActivityHistory activity = new ActivityHistory("Returning books", LocalDateTime.now(),
        bookBorrowingList.get(0).getUser());
    activityHistoryRepository.save(activity);

    for (BookBorrowing bookBorrowing : bookBorrowingList) {
      bookBorrowing.setReturnDate(now);
      long numberOfOverdueDays = Duration.between(bookBorrowing.getBorrowDate(), now).toDays() - 180L;
//      int numberOfOverdueDays = now.compareTo(bookBorrowing.getBorrowDate()) - 180;
      if (numberOfOverdueDays > 0) {
        bookBorrowing.setTotalPaymentDue(numberOfOverdueDays * 1000L);
      } else {
        bookBorrowing.setTotalPaymentDue(0L);
      }
      Book book = bookBorrowing.getBook();
      if (book == null) {
        throw new AppException(404, 44, "Error: Does not exist! Book not found!");
      } else if (Objects.equals(book.getStatus(), "Available")) {
        throw new AppException(400, 4, "You can only return books that haven't been returned yet!");
      }
      book.setStatus("Available");
      bookRepository.save(book);

      BookCategories bookCategories = book.getBookCategories();
      bookCategories.setAvailableQuantity(bookCategories.getAvailableQuantity() + 1);
      bookCategoriesRepository.save(bookCategories);
    }
    return new SuccessResponse(bookBorrowingRepository.saveAll(bookBorrowingList).stream()
        .map(bookBorrowing -> mapper.map(bookBorrowing, BookBorrowingDto.class)).collect(
            Collectors.toList()));
  }

  @Override
  public Map<String, Object> payment(PaymentDto paymentDto) {
    User user = userRepository.findById(paymentDto.getUserId()).orElse(null);
    if (user == null) {
      throw new AppException(404, 44, "Error: Does not exist! User not found!");
    }

    ActivityHistory activity = new ActivityHistory("Returning books", LocalDateTime.now(), user);
    activityHistoryRepository.save(activity);

    bookBorrowingRepository.payment(paymentDto.getBookId(), paymentDto.getAmountPaid(),
        paymentDto.getUserId()).orElseThrow(
        () -> new AppException(404, 44, "Error: Does not exist! Book borrowing not found!"));
    final Map<String, Object> body = new HashMap<>();
    body.put("code", 0);
    body.put("message", "Successfully!");
    return body;
  }
}
