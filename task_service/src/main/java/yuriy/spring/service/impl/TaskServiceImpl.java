package yuriy.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yuriy.spring.dto.TaskCreateDto;
import yuriy.spring.dto.TaskUpdateDto;
import yuriy.spring.dto.TaskViewDto;
import yuriy.spring.entity.TaskStatus;
import yuriy.spring.exception.TaskNotFoundException;
import yuriy.spring.mapper.TaskMapper;
import yuriy.spring.repository.TaskRepository;
import yuriy.spring.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskViewDto create(TaskCreateDto taskDto) {
        var task = taskRepository.save(taskMapper.fromDto(taskDto));
        return taskMapper.fromEntity(task);
    }

    @Override
    public List<TaskViewDto> getTasksByUserId(Long ownerId) {
        return taskMapper.fromEntityList(
                taskRepository.findAllByOwnerId(ownerId));
    }

    @Override
    public void delete(Long taskId, Long ownerId) {
        taskRepository.deleteByIdAndOwnerId(taskId, ownerId);
    }

    @Override
    public TaskViewDto getTask(Long taskId, Long ownerId) {
        return taskRepository.findByIdAndOwnerId(taskId, ownerId)
                .map(taskMapper::fromEntity)
                .orElseThrow(() -> new TaskNotFoundException("task.service.errors.task_not_found"));
    }

    @Override
    @Transactional
    public void update(TaskUpdateDto taskDto) {
        var task = taskRepository.findByIdAndOwnerId(taskDto.getId(), taskDto.getOwnerId())
                .orElseThrow(() -> new TaskNotFoundException("task.service.errors.task_not_found"));
        task.setDescription(taskDto.getDescription());
        task.setTitle(taskDto.getTitle());
    }

    @Override
    @Transactional
    public void completeTask(Long taskId, Long ownerId) {
        var task = taskRepository.findByIdAndOwnerId(taskId, ownerId)
                .orElseThrow(() -> new TaskNotFoundException("task.service.errors.task_not_found"));
        task.setStatus(TaskStatus.COMPLETED);
        task.setCompletedAt(LocalDateTime.now());
    }
}
