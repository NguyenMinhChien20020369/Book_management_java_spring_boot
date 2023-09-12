package com.chien.bookManagement.dto;

import com.chien.bookManagement.entity.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoriesDto {
  private Long id;

  private String title;

  private String author;

  private Long amount;

  private Long availableQuantity;

  private Collection<Long> bookIds;

}
