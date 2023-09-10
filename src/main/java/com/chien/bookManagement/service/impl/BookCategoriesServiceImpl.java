package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.entity.User;
import com.chien.bookManagement.entity.UserDetailsImpl;
import com.chien.bookManagement.exception.AppException;
import com.chien.bookManagement.repository.BookCategoriesRepository;
import com.chien.bookManagement.service.BookCategoriesService;
import com.chien.bookManagement.service.UserService;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookCategoriesServiceImpl implements BookCategoriesService {

  @Autowired
  private BookCategoriesRepository bookCategoriesRepository;
  @Autowired
  private ModelMapper mapper;

  @Override
  public BookCategories create(BookCategories bookCategoriesInput) {
    BookCategories bookCategories = new BookCategories(bookCategoriesInput);
    return bookCategoriesRepository.save(mapper.map(bookCategories, BookCategories.class));
  }

  @Override
  public BookCategories update(Long id, BookCategories bookCategoriesInput) {
    BookCategories fromDB = bookCategoriesRepository.findById(id).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, "User not found");
    }
    fromDB.setId(bookCategoriesInput.getId());
    fromDB.setId(bookCategoriesInput.getId());
    fromDB.setId(bookCategoriesInput.getId());
    fromDB.setId(bookCategoriesInput.getId());
    return bookCategoriesRepository.save(fromDB);
  }

  @Override
  public BookCategories delete(Long id) {
    BookCategories fromDB = bookCategoriesRepository.findById(id).orElse(null);
    if (fromDB == null) {
      throw new AppException(404, "User not found");
    }
    bookCategoriesRepository.deleteById(id);
    return fromDB;
  }

  @Override
  public BookCategories findById(Long id) {
    BookCategories user = bookCategoriesRepository.findById(id).orElse(null);
    if (user == null) {
      throw new AppException(404, "User not found");
    } else {
      return user;
    }
  }

  @Override
  public Iterable<BookCategories> findAll() {
    List<BookCategories> userList = bookCategoriesRepository.findAll();
    if (userList.isEmpty()) {
      throw new AppException(404, "No user has been created yet!");
    }
    return userList;
  }

  @Override
  public List<BookCategories> findByTitleOrAuthor(String name) {
    return bookCategoriesRepository.findByTitleOrAuthor("%" + name + "%");
  }
}
