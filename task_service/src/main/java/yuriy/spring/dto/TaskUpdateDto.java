package yuriy.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yuriy.spring.entity.TaskStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateDto {

    private Long id;
    private Long ownerId;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime completedAt;

}
