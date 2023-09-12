package com.chien.bookManagement.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityHistoryDto {
  private Long id;

  private String activityName;

  private LocalDateTime time;

  private Long userId;

}
