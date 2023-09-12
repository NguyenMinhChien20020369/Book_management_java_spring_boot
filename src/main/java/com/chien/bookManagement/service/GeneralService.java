package com.chien.bookManagement.service;

import com.chien.bookManagement.payload.response.MessageResponse;
import java.util.List;

public interface GeneralService<D, C, U> {
  D create(C c);

  D update(U u);

  MessageResponse delete(Long id);

  D findById(Long id);

  Iterable<D> findAll();
}
