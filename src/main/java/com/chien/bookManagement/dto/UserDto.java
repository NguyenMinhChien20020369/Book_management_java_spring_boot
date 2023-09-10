package com.chien.bookManagement.dto;

import com.chien.bookManagement.entity.Role;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

  private Long id;

  private String username;

  private String name;

  private String email;

  private String phone;

  private String address;
  private Boolean enabled;
}
