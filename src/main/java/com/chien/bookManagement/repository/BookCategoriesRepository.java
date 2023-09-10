package com.chien.bookManagement.repository;

import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.entity.User;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoriesRepository extends JpaRepository<BookCategories, Long> {
  @Query(value = "SELECT * FROM book_categories WHERE title LIKE :name OR author LIKE :name ORDER BY author, title", nativeQuery = true)
  List<BookCategories> findByTitleOrAuthor(@Param("name") String name);
}
