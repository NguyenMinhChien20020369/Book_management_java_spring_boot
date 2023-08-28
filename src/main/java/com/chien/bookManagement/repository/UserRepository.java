package com.chien.bookManagement.repository;

import com.chien.bookManagement.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByName(String name);
  List<User> findByPhone(String phone);
}
