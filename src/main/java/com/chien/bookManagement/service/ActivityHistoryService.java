package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.ActivityHistoryDto;
import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.entity.ActivityHistory;
import java.util.List;

public interface ActivityHistoryService {
  List<ActivityHistoryDto> viewUserActivity (Long id);
}
