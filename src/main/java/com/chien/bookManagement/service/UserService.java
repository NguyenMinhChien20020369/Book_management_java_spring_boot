package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends
    com.chien.demoPerson.service.GeneralService<UserDto, UserCreationDto, UserUpdateDto>,
    UserDetailsService {

  List<UserDto> findByPhone(String phone);
}
