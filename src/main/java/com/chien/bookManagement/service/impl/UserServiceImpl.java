package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import com.chien.bookManagement.entity.User;
import com.chien.bookManagement.exception.AppException;
import com.chien.bookManagement.repository.UserRepository;
import com.chien.bookManagement.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ModelMapper mapper;

  @Override
  public UserDto create(UserCreationDto userCreationDto) {
    return mapper.map(userRepository.save(mapper.map(userCreationDto, User.class)),
        UserDto.class);
  }

  @Override
  public UserDto update(Long id, UserUpdateDto userUpdateDto) {
    User fromDB = userRepository.findById(id).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, "User not found");
    }
    fromDB.setEmail(userUpdateDto.getEmail());
    fromDB.setName(userUpdateDto.getName());
    fromDB.setPhone(userUpdateDto.getPhone());
    fromDB.setAddress(userUpdateDto.getAddress());
    return mapper.map(userRepository.save(fromDB), UserDto.class);
  }

  @Override
  public UserDto delete(Long id) {
    User fromDB = userRepository.findById(id).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, "User not found");
    }
    userRepository.deleteById(id);
    return mapper.map(fromDB, UserDto.class);
  }

  @Override
  public UserDto findById(Long id) {
    User user = userRepository.findById(id).orElse(null);
    if (user == null) {
      throw new AppException(404, "User not found");
    } else {
      return mapper.map(user, UserDto.class);
    }
  }

  @Override
  public List<UserDto> findByName(String name) {
    List<User> userList = userRepository.findByName(name);
    if (userList.isEmpty()) {
      throw new AppException(404, "User not found with name \"" + name + "\"");
    }
    return userList.stream()
        .map(user -> mapper.map(user, UserDto.class)).collect(
            Collectors.toList());
  }

  @Override
  public List<UserDto> findByPhone(String phone) {
    List<User> userList = userRepository.findByPhone(phone);
    if (userList.isEmpty()) {
      throw new AppException(404, "User not found with phone \"" + phone + "\"");
    }
    return userRepository.findByPhone(phone).stream()
        .map(user -> mapper.map(user, UserDto.class)).collect(
            Collectors.toList());
  }

  @Override
  public Iterable<UserDto> findAll() {
    List<User> userList =userRepository.findAll();
    if (userList.isEmpty()) {
      throw new AppException(404, "No user has been created yet!");
    }
    return userList.stream()
        .map(groupOfPeople -> mapper.map(groupOfPeople, UserDto.class)).collect(
            Collectors.toList());
  }
}
