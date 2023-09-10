package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.BookBorrowingDto;
import com.chien.bookManagement.dto.BorrowingBooksDto;
import com.chien.bookManagement.dto.PaymentDto;
import com.chien.bookManagement.dto.ReturningBooksDto;
import com.chien.bookManagement.entity.BookBorrowing;
import com.chien.bookManagement.entity.BookCategories;
import java.util.List;

public interface UserActivitiesService {
  List<BookBorrowingDto> returningBooks(ReturningBooksDto returningBooksDto);
  String payment(PaymentDto paymentDto);
}
