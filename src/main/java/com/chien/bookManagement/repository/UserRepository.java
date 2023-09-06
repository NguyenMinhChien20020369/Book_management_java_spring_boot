package com.chien.bookManagement.repository;

import com.chien.bookManagement.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByName(String name);
  List<User> findByPhone(String phone);
  Optional<User> findByUsername(String username);
  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
