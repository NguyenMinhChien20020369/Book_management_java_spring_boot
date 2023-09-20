package com.chien.bookManagement.service;

import com.chien.bookManagement.dto.ActivityHistoryDto;
import com.chien.bookManagement.dto.BookCreationDto;
import com.chien.bookManagement.entity.ActivityHistory;
import com.chien.bookManagement.payload.response.SuccessResponse;
import java.util.List;

public interface ActivityHistoryService {
  SuccessResponse viewUserActivity (Long id);
}
