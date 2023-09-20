package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.dto.BookCategoriesDto;
import com.chien.bookManagement.dto.BookCategoriesUpdateDto;
import com.chien.bookManagement.entity.ActivityHistory;
import com.chien.bookManagement.entity.Book;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.entity.User;
import com.chien.bookManagement.entity.UserDetailsImpl;
import com.chien.bookManagement.exception.AppException;
import com.chien.bookManagement.payload.response.SuccessResponse;
import com.chien.bookManagement.repository.ActivityHistoryRepository;
import com.chien.bookManagement.repository.BookCategoriesRepository;
import com.chien.bookManagement.repository.UserRepository;
import com.chien.bookManagement.service.BookCategoriesService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookCategoriesServiceImpl implements BookCategoriesService {

  @Autowired
  private BookCategoriesRepository bookCategoriesRepository;
  @Autowired
  private ActivityHistoryRepository activityHistoryRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ModelMapper mapper;

  @Override
  public BookCategoriesDto create(BookCategories bookCategoriesInput) {
    BookCategories bookCategories = new BookCategories(bookCategoriesInput);
    return mapper.map(
        bookCategoriesRepository.save(mapper.map(bookCategories, BookCategories.class)),
        BookCategoriesDto.class);
  }

  @Override
  public SuccessResponse update(BookCategoriesUpdateDto bookCategoriesUpdateDto) {
    BookCategories fromDB = bookCategoriesRepository.findById(bookCategoriesUpdateDto.getId())
        .orElseThrow(
            () -> new AppException(404, 44, "Error: Does not exist! Book categories not found!"));
    fromDB.setTitle(bookCategoriesUpdateDto.getTitle());
    fromDB.setAuthor(bookCategoriesUpdateDto.getAuthor());
    BookCategoriesDto result = mapper.map(bookCategoriesRepository.save(fromDB),
        BookCategoriesDto.class);
    result.setBookIds(fromDB.getBooks().stream().map(Book::getId).collect(Collectors.toList()));
    return new SuccessResponse(result);
  }

  @Override
  public Map<String, Object> delete(Long id) {
    BookCategories bookCategories = bookCategoriesRepository.findById(id)
        .orElseThrow(
            () -> new AppException(404, 44, "Error: Does not exist! Book categories not found!"));

    bookCategoriesRepository.deleteById(id);
    final Map<String, Object> body = new HashMap<>();
    body.put("code", 0);
    body.put("message", "Successfully deleted!");
    return body;
  }

  @Override
  public SuccessResponse findById(Long id) {
    BookCategories user = bookCategoriesRepository.findById(id).orElse(null);
    if (user == null) {
      throw new AppException(404, 44, "Error: Does not exist! Book categories not found!");
    } else {
      return new SuccessResponse(mapper.map(user, BookCategoriesDto.class));
    }
  }

  @Override
  public SuccessResponse findAll() {
    List<BookCategories> bookCategoriesList = bookCategoriesRepository.findAll();
    if (bookCategoriesList.isEmpty()) {
      throw new AppException(404, 44,
          "Error: Does not exist! No book categories has been created yet!");
    }
    return new SuccessResponse(bookCategoriesList.stream()
        .map(bookCategories -> mapper.map(bookCategories, BookCategoriesDto.class)).collect(
            Collectors.toList()));
  }

  @Override
  public SuccessResponse findByTitleOrAuthor(String name) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(
            () -> new UsernameNotFoundException(
                "User not found with username: " + userDetails.getUsername() + "!"));
    ActivityHistory activity = new ActivityHistory("Find by title or author", LocalDateTime.now(),
        user);
    activityHistoryRepository.save(activity);
    List<BookCategories> bookCategoriesList = bookCategoriesRepository.findByTitleOrAuthor(
        "%" + name + "%");
    List<BookCategoriesDto> bookCategoriesDtoList = new ArrayList<>();
    for (BookCategories bookCategories : bookCategoriesList) {
      BookCategoriesDto bookCategoriesDto = mapper.map(bookCategories, BookCategoriesDto.class);
      bookCategoriesDto.setBookIds(bookCategories.getBooks().stream().map(Book::getId).collect(
          Collectors.toList()));
      bookCategoriesDtoList.add(bookCategoriesDto);
    }
    if (bookCategoriesList.isEmpty()) {
      throw new AppException(404, 44,
          "Book categories not found with title or author name contain '" + name + "'!");
    }
    return new SuccessResponse(bookCategoriesDtoList);
  }
}
