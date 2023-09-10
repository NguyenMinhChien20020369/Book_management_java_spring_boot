package com.chien.bookManagement.repository;

import com.chien.bookManagement.entity.Book;
import com.chien.bookManagement.entity.BookCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
