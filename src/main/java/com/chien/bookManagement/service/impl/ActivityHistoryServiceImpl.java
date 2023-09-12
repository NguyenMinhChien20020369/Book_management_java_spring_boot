package com.chien.bookManagement.service.impl;

import com.chien.bookManagement.dto.ActivityHistoryDto;
import com.chien.bookManagement.dto.BookCategoriesDto;
import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.entity.ActivityHistory;
import com.chien.bookManagement.entity.Book;
import com.chien.bookManagement.entity.BookCategories;
import com.chien.bookManagement.repository.ActivityHistoryRepository;
import com.chien.bookManagement.repository.BookCategoriesRepository;
import com.chien.bookManagement.service.ActivityHistoryService;
import com.chien.bookManagement.service.BookCategoriesService;
import com.chien.bookManagement.service.BookService;
import com.chien.bookManagement.service.LibrarianService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityHistoryServiceImpl implements ActivityHistoryService {

  @Autowired
  private BookCategoriesService bookCategoriesService;

  @Autowired
  private BookService bookService;

  @Autowired
  private BookCategoriesRepository bookCategoriesRepository;

  @Autowired
  private ActivityHistoryRepository activityHistoryRepository;

  @Autowired
  private ModelMapper mapper;

  @Override
  public List<ActivityHistoryDto> viewUserActivity(Long id) {
    return activityHistoryRepository.findByUserId(id).stream()
        .map(activityHistory -> {
          ActivityHistoryDto result = mapper.map(activityHistory,
              ActivityHistoryDto.class);
          result.setUserId(activityHistory.getUser().getId());
          return result;
        }).collect(Collectors.toList());
  }
}
