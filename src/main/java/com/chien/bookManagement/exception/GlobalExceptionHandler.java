package com.chien.bookManagement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({AppException.class})
  public ResponseEntity<String> handleAppException(AppException e) {
    return ResponseEntity.status(e.getCode()).body(e.getMessage());
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<String> handleBindException(BindException e) {
    if (e.getBindingResult().hasErrors()) {
      System.out.println(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
    return ResponseEntity.status(400).body("Invalid request");
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleUnwantedException(Exception e) {
    e.printStackTrace();
    return ResponseEntity.status(500).body("Unknown error");
  }
}
