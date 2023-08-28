package com.chien.bookManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateDto {

  @NotEmpty(message = "Thiếu tên!")
  private String name;
  @Email(message = "Email không hợp lệ!")
  private String email;
  @NotEmpty(message = "Thiếu số điện thoại!")
  @Size(min = 10, message = "Số điện thoại phải có 10 số trở lên!")
  private String phone;
  @NotEmpty(message = "Thiếu địa chỉ!")
  private String address;
}
