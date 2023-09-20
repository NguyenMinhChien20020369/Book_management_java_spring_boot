package com.chien.bookManagement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OldBookAddDto {

  @NotNull(message = "Thiếu ID danh mục sách!")
  private Long id;

  @NotNull(message = "Thiếu số lượng cần thêm!")
  private Long addQuantity;
}
