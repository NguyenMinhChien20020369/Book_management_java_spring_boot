package com.chien.bookManagement.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException e) {
    System.out.println("Unauthorized error: "+ e.getMessage());


    final Map<String, Object> body = new HashMap<>();
    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
    body.put("error", "Unauthorized");
    body.put("message", e.getMessage());
    return ResponseEntity.status(401).contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE)).body(body);
  }

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
