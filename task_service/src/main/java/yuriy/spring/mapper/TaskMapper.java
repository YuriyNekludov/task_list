package yuriy.spring.mapper;

import org.mapstruct.Mapper;
import yuriy.spring.dto.TaskCreateDto;
import yuriy.spring.dto.TaskViewDto;
import yuriy.spring.entity.Task;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task fromDto(TaskCreateDto taskDto);

    TaskViewDto fromEntity(Task task);

    List<TaskViewDto> fromEntityList(List<Task> tasks);
}
