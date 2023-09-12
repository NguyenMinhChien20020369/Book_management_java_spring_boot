package com.chien.bookManagement.repository;

import com.chien.bookManagement.entity.User;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.QueryRewriterProvider;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByName(String name);
  List<User> findByPhone(String phone);
  Optional<User> findByUsername(String username);
  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  List<User> findByEnabled(Boolean enabled);
  @Modifying
  @Transactional
  @Query(value = "UPDATE user SET enabled = :enabled WHERE id IN :ids", nativeQuery = true)
  Optional<Integer> updateEnabledById(@Param("ids") Collection<Long> ids, @Param("enabled") Boolean enabled);
  @Modifying
  @Transactional
  @Query(value = "UPDATE user SET disabled = :disabled WHERE id IN :ids", nativeQuery = true)
  Optional<Integer> updateDisabledById(@Param("ids") Collection<Long> ids, @Param("disabled") Boolean disabled);
}
