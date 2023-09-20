package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.BookBorrowingDto;
import com.chien.bookManagement.dto.BorrowingBooksDto;
import com.chien.bookManagement.dto.PaymentDto;
import com.chien.bookManagement.dto.ReturningBooksDto;
import com.chien.bookManagement.entity.BookBorrowing;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.payload.response.SuccessResponse;
import java.util.List;
import java.util.Map;

public interface UserActivitiesService {
  SuccessResponse returningBooks(ReturningBooksDto returningBooksDto);
  Map<String, Object> payment(PaymentDto paymentDto);
}
