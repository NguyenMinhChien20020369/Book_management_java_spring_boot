package com.chien.bookManagement.dto;

import com.chien.bookManagement.entity.Book;
import com.chien.bookManagement.entity.Role;
import com.chien.bookManagement.entity.User;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookBorrowingDto {

  private Long id;

  private LocalDateTime borrowDate;

  private LocalDateTime returnDate;

  private Long totalPaymentDue;

  private Long amountPaid;
}
