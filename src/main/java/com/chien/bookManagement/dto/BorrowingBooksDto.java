package com.chien.bookManagement.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BorrowingBooksDto {
  @NotEmpty(message = "Missing user ID!")
  private Long userId;

  @NotEmpty(message = "Missing book ID!")
  private Long bookId;
}
