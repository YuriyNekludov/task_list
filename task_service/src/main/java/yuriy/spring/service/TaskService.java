package yuriy.spring.service;

import yuriy.spring.dto.TaskCreateDto;
import yuriy.spring.dto.TaskUpdateDto;
import yuriy.spring.dto.TaskViewDto;

import java.util.List;

public interface TaskService {

    TaskViewDto create(TaskCreateDto taskDto);

    List<TaskViewDto> getTasksByUserId(Long ownerId);

    void delete(Long taskId, Long ownerId);

    TaskViewDto getTask(Long taskId, Long ownerId);

    void update(TaskUpdateDto taskDto);

    void completeTask(Long taskId, Long ownerId);
}
