package com.chien.bookManagement.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse {

  private Long code = 0L;
  private String message = "Success!";
  private Object data;

  public SuccessResponse(Object data) {
    this.data = data;
  }
}
