package com.chien.bookManagement.dto;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class ReturningBooksDto {
  @NotEmpty(message = "Missing book IDS!")
  private Collection<Long> bookIds;
}
