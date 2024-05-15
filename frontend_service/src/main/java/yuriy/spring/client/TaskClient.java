package yuriy.spring.client;

import yuriy.spring.entity.TaskViewDto;

import java.util.List;

public interface TaskClient {

    List<TaskViewDto> getTasks(String accessToken);
}
