package com.chien.bookManagement.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentDto {
  @NotEmpty(message = "Missing user ID!")
  private Long userId;

  @NotEmpty(message = "Missing book ID!")
  private Long bookId;

  @NotEmpty(message = "Missing amount paid!")
  private Long amountPaid;
}
