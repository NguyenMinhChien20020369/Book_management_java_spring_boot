package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import com.chien.bookManagement.entity.ActivityHistory;
import com.chien.bookManagement.entity.User;
import com.chien.bookManagement.entity.UserDetailsImpl;
import com.chien.bookManagement.exception.AppException;
import com.chien.bookManagement.payload.response.MessageResponse;
import com.chien.bookManagement.repository.ActivityHistoryRepository;
import com.chien.bookManagement.repository.UserRepository;
import com.chien.bookManagement.service.UserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ActivityHistoryRepository activityHistoryRepository;
  @Autowired
  private ModelMapper mapper;

  @Override
  public UserDto create(UserCreationDto userCreationDto) {
    return mapper.map(userRepository.save(mapper.map(userCreationDto, User.class)),
        UserDto.class);
  }

  @Override
  public UserDto update(UserUpdateDto userUpdateDto) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    User thisUser = userRepository.findById(userDetails.getId())
        .orElseThrow(() -> new AppException(404, "User not found"));

    ActivityHistory activity = new ActivityHistory("Update user", LocalDateTime.now(), thisUser);
    activityHistoryRepository.save(activity);

    User fromDB = userRepository.findById(userUpdateDto.getId())
        .orElseThrow(() -> new AppException(404, "User not found"));

    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority).toList();
    boolean isUser = false;
    boolean isLib = false;
    for (String role : roles) {
      if (Objects.equals(role, "USER")) {
        isUser = true;
      } else if (Objects.equals(role, "LIBRARIAN")) {
        isLib = true;
      }
    }
    if (!isLib && isUser) {
      if (!Objects.equals(userUpdateDto.getId(), userDetails.getId())) {
        throw new AppException(401, "Unauthorized");
      }
    }

    fromDB.setEmail(userUpdateDto.getEmail());
    fromDB.setName(userUpdateDto.getName());
    fromDB.setPhone(userUpdateDto.getPhone());
    fromDB.setAddress(userUpdateDto.getAddress());
    return mapper.map(userRepository.save(fromDB), UserDto.class);
  }

  @Override
  public MessageResponse delete(Long id) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    User thisUser = userRepository.findById(userDetails.getId())
        .orElseThrow(() -> new AppException(404, "User not found"));

    ActivityHistory activity = new ActivityHistory("Delete user", LocalDateTime.now(), thisUser);
    activityHistoryRepository.save(activity);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority).toList();
    boolean isUser = false;
    boolean isLib = false;
    for (String role : roles) {
      if (Objects.equals(role, "USER")) {
        isUser = true;
      } else if (Objects.equals(role, "LIBRARIAN")) {
        isLib = true;
      }
    }
    if (!isLib && isUser) {
      if (!Objects.equals(id, userDetails.getId())) {
        throw new AppException(401, "Unauthorized");
      }
    }

    User fromDB = userRepository.findById(id).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, "User not found");
    }
    userRepository.deleteById(id);
    return new MessageResponse("Successfully deleted!");
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
    return userList.stream()
        .map(user -> mapper.map(user, UserDto.class)).collect(
            Collectors.toList());
  }

  @Override
  public List<UserDto> findByEnabled(Boolean enabled) {
    List<User> userList = userRepository.findByEnabled(enabled);
    if (userList.isEmpty()) {
      throw new AppException(404, "User not found with enabled \"" + enabled + "\"");
    }
    return userList.stream()
        .map(user -> mapper.map(user, UserDto.class)).collect(
            Collectors.toList());
  }

  @Override
  public String acceptAccount(Collection<Long> ids) {
    Integer rowCount = userRepository.updateEnabledById(ids, true)
        .orElseThrow(() -> new AppException(400, "Error when accepting accounts!"));
    return "Successfully accepted " + rowCount + " accounts!";
  }

  @Override
  public String rejectAccount(Collection<Long> ids) {
    userRepository.deleteAllByIdInBatch(ids);
    return "Successfully!";
  }

  @Override
  public String disabledAccount(Collection<Long> ids) {
    Integer rowCount = userRepository.updateDisabledById(ids, true)
        .orElseThrow(() -> new AppException(400, "Error when disabling accounts!"));
    return "Successfully disabled " + rowCount + " accounts!";
  }

  @Override
  public Iterable<UserDto> findAll() {
    List<User> userList = userRepository.findAll();
    if (userList.isEmpty()) {
      throw new AppException(404, "No user has been created yet!");
    }
    return userList.stream()
        .map(user -> mapper.map(user, UserDto.class)).collect(
            Collectors.toList());
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }
}
