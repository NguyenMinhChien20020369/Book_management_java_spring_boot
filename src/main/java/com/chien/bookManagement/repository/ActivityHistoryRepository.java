package com.chien.bookManagement.repository;

import com.chien.bookManagement.entity.ActivityHistory;
import com.chien.bookManagement.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityHistoryRepository extends JpaRepository<ActivityHistory, Long> {
  List<ActivityHistory> findByUserId(Long userId);
}
