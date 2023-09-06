package com.chien.bookManagement.repository;

import com.chien.bookManagement.entity.ERole;
import com.chien.bookManagement.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
