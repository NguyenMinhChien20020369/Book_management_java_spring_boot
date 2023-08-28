package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import java.util.List;

public interface UserService extends com.chien.demoPerson.service.GeneralService<UserDto, UserCreationDto, UserUpdateDto> {
  List<UserDto> findByPhone(String phone);
}
