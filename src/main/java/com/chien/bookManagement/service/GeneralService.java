package com.chien.demoPerson.service;

import java.util.List;

public interface GeneralService<D, C, U> {
  D create(C c);

  D update(Long id, U u);

  D delete(Long id);

  D findById(Long id);
  List<D> findByName(String name);

  Iterable<D> findAll();
}
