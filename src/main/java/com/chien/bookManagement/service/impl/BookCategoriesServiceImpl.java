package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.dto.BookCategoriesDto;
import com.chien.bookManagement.dto.BookCategoriesUpdateDto;
import com.chien.bookManagement.dto.UserCreationDto;
import com.chien.bookManagement.dto.UserDto;
import com.chien.bookManagement.dto.UserUpdateDto;
import com.chien.bookManagement.entity.ActivityHistory;
import com.chien.bookManagement.entity.Book;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.entity.User;
import com.chien.bookManagement.entity.UserDetailsImpl;
import com.chien.bookManagement.exception.AppException;
import com.chien.bookManagement.payload.response.MessageResponse;
import com.chien.bookManagement.repository.ActivityHistoryRepository;
import com.chien.bookManagement.repository.BookCategoriesRepository;
import com.chien.bookManagement.repository.UserRepository;
import com.chien.bookManagement.service.BookCategoriesService;
import com.chien.bookManagement.service.UserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
  public BookCategoriesDto update(BookCategoriesUpdateDto bookCategoriesUpdateDto) {
    BookCategories fromDB = bookCategoriesRepository.findById(bookCategoriesUpdateDto.getId())
        .orElseThrow(() -> new AppException(404, "User not found"));
    fromDB.setTitle(bookCategoriesUpdateDto.getTitle());
    fromDB.setAuthor(bookCategoriesUpdateDto.getAuthor());
    BookCategoriesDto result = mapper.map(bookCategoriesRepository.save(fromDB),
        BookCategoriesDto.class);
    result.setBookIds(fromDB.getBooks().stream().map(Book::getId).collect(Collectors.toList()));
    return result;
  }

  @Override
  public MessageResponse delete(Long id) {
    bookCategoriesRepository.findById(id)
        .orElseThrow(() -> new AppException(404, "Book categories not found!"));
    bookCategoriesRepository.deleteById(id);
    return new MessageResponse("Successfully deleted!");
  }

  @Override
  public BookCategoriesDto findById(Long id) {
    BookCategories user = bookCategoriesRepository.findById(id).orElse(null);
    if (user == null) {
      throw new AppException(404, "User not found");
    } else {
      return mapper.map(user, BookCategoriesDto.class);
    }
  }

  @Override
  public Iterable<BookCategoriesDto> findAll() {
    List<BookCategories> bookCategoriesList = bookCategoriesRepository.findAll();
    if (bookCategoriesList.isEmpty()) {
      throw new AppException(404, "No user has been created yet!");
    }
    return bookCategoriesList.stream()
        .map(bookCategories -> mapper.map(bookCategories, BookCategoriesDto.class)).collect(
            Collectors.toList());
  }

  @Override
  public List<BookCategories> findByTitleOrAuthor(String name) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(
            () -> new UsernameNotFoundException(
                "User Not Found with username: " + userDetails.getUsername()));
    ActivityHistory activity = new ActivityHistory("Find by title or author", LocalDateTime.now(), user);
    activityHistoryRepository.save(activity);
    return bookCategoriesRepository.findByTitleOrAuthor("%" + name + "%");
  }
}
