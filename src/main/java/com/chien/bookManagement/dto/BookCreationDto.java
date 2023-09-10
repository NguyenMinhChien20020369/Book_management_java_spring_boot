package com.chien.bookManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookCreationDto {

  @NotEmpty(message = "Thiếu tên sách!")
  private String title;

  @NotEmpty(message = "Thiếu tên tác giả!")
  private String author;

  @NotEmpty(message = "Thiếu số lượng!")
  private Long amount;
}
