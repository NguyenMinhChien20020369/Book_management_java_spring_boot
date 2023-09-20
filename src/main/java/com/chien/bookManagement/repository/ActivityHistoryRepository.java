package com.chien.bookManagement.repository;

import com.chien.bookManagement.entity.ActivityHistory;
import com.chien.bookManagement.entity.Book;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityHistoryRepository extends JpaRepository<ActivityHistory, Long> {
  List<ActivityHistory> findByUserId(Long userId);
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM activity_history WHERE user_id IN :ids", nativeQuery = true)
  void deleteAllByUserIdInBatch(@Param("ids") Collection<Long> ids);
}
