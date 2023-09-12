package com.chien.bookManagement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BorrowingBooksDto {
  @NotNull(message = "Missing user ID!")
  private Long userId;

  @NotNull(message = "Missing book ID!")
  private Long bookId;
}
