package com.chien.bookManagement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoriesUpdateDto {
  @NotNull(message = "Thiếu ID danh mục sách!")
  private Long id;

  @NotEmpty(message = "Thiếu tên sách!")
  private String title;

  @NotEmpty(message = "Thiếu tên tác giả!")
  private String author;

}
