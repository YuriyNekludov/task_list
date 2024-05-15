package yuriy.spring.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import yuriy.spring.client.TaskClient;
import yuriy.spring.entity.TaskViewDto;

import java.util.List;

@RequiredArgsConstructor
public class TaskClientImpl implements TaskClient {

    private final RestClient restClient;

    @Override
    public List<TaskViewDto> getTasks(String accessToken) {
        return restClient.get()
                .uri("/api/v1/tasks")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
