package yuriy.spring.entity;

import java.time.LocalDateTime;

public record TaskViewDto(String title,
                          String description,
                          String status,
                          LocalDateTime completedAt) {
}
