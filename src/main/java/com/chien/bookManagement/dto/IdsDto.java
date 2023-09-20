package com.chien.bookManagement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IdsDto {
  @NotNull(message = "List of IDs is missing! You need to add the 'ids' field to JSON object!")
  @NotEmpty(message = "List of IDs is empty!")
  private Collection<Long> ids;
}
