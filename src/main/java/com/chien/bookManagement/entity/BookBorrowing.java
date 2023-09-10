package com.chien.bookManagement.entity;

import com.chien.bookManagement.dto.BorrowingBooksDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookBorrowing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  private LocalDateTime borrowDate;

  private LocalDateTime returnDate;

  private Long totalPaymentDue;

  private Long amountPaid;

  public BookBorrowing(User user, Book book) {
    this.user = user;
    this.book = book;
    this.borrowDate = LocalDateTime.now();
  }
}
