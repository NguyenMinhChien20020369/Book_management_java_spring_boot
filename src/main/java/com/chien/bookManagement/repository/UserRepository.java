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

  @Query(value = "SELECT * FROM user WHERE name LIKE :name", nativeQuery = true)
  List<User> findByName(@Param("name") String name);

  @Query(value = "SELECT * FROM user WHERE phone LIKE :phone", nativeQuery = true)
  List<User> findByPhone(@Param("phone") String phone);

  @Query(value = "SELECT * FROM user WHERE BINARY(username) = BINARY(:username)", nativeQuery = true)
  Optional<User> findByUsername(@Param("username") String username);

  @Query(value = "SELECT * FROM user WHERE BINARY(email) = BINARY(:email)", nativeQuery = true)
  Optional<User> findByEmail(@Param("email") String email);

  List<User> findByEnabled(Boolean enabled);

  @Modifying
  @Transactional
  @Query(value = "UPDATE user SET enabled = :enabled WHERE id IN :ids", nativeQuery = true)
  Optional<Integer> updateEnabledById(@Param("ids") Collection<Long> ids,
      @Param("enabled") Boolean enabled);

  @Modifying
  @Transactional
  @Query(value = "UPDATE user SET disabled = :disabled WHERE id IN :ids", nativeQuery = true)
  Optional<Integer> updateDisabledById(@Param("ids") Collection<Long> ids,
      @Param("disabled") Boolean disabled);
}
