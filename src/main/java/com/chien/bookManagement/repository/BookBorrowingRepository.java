package com.chien.bookManagement.repository;

import com.chien.bookManagement.entity.Book;
import com.chien.bookManagement.entity.BookBorrowing;
import com.chien.bookManagement.entity.BookCategories;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookBorrowingRepository extends JpaRepository<BookBorrowing, Long> {

  @Query(value = "SELECT * FROM book_borrowing WHERE return_date IS NULL AND book_id IN :bookIds", nativeQuery = true)
  Optional<List<BookBorrowing>> findBooksToBeReturnedByBookIds(
      @Param("bookIds") Collection<Long> bookIds);

  @Modifying
  @Transactional
  @Query(value = "UPDATE book_borrowing SET amount_paid = :amountPaid WHERE (amount_paid IS NULL OR amount_paid < total_payment_due) AND book_id = :bookId AND user_id = :userId", nativeQuery = true)
  Optional<Integer> payment(@Param("bookId") Long bookId,
      @Param("amountPaid") Long amountPaid,
      @Param("userId") Long userId);
}
