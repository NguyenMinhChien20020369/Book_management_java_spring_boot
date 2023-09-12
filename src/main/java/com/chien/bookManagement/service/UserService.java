package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends
    GeneralService<UserDto, UserCreationDto, UserUpdateDto>,
    UserDetailsService {
  List<UserDto> findByName(String name);

  List<UserDto> findByPhone(String phone);
  List<UserDto> findByEnabled(Boolean enabled);
  String acceptAccount(Collection<Long> ids);
  String rejectAccount(Collection<Long> ids);
  String disabledAccount(Collection<Long> ids);
}
