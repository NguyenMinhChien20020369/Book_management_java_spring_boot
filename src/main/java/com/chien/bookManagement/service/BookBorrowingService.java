package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.BookBorrowingDto;
import com.chien.bookManagement.dto.BorrowingBooksDto;
import com.chien.bookManagement.entity.BookBorrowing;
import com.chien.bookManagement.entity.BookCategories;
import java.util.List;

public interface BookBorrowingService extends
    com.chien.demoPerson.service.GeneralService<BookBorrowingDto, BorrowingBooksDto, BookBorrowing> {
}
