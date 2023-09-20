package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import com.chien.bookManagement.payload.response.SuccessResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends
    GeneralService<UserDto, UserCreationDto, UserUpdateDto>,
    UserDetailsService {
  SuccessResponse findByName(String name);

  SuccessResponse findByPhone(String phone);
  SuccessResponse findByEnabled(Boolean enabled);
  Map<String, Object> acceptAccount(Collection<Long> ids);
  Map<String, Object> rejectAccount(Collection<Long> ids);
  Map<String, Object> disabledAccount(Collection<Long> ids);
}
