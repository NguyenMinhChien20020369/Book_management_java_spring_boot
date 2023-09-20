package com.chien.bookManagement.service;

import com.chien.bookManagement.payload.response.SuccessResponse;
import java.util.Map;

public interface GeneralService<D, C, U> {
  D create(C c);

  SuccessResponse update(U u);

  Map<String, Object> delete(Long id);

  SuccessResponse findById(Long id);

  SuccessResponse findAll();
}
