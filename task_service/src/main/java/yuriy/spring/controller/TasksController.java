package yuriy.spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yuriy.spring.dto.TaskCreateDto;
import yuriy.spring.dto.TaskViewDto;
import yuriy.spring.service.TaskService;
import yuriy.spring.util.TokenParser;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TasksController {

    private final TaskService taskService;
    private final TokenParser tokenParser;

    @ModelAttribute("ownerId")
    public Long getOwnerId(@RequestHeader(value = "Authorization") String token) {
        return Long.valueOf(tokenParser.getOwnerIdFromToken(token));
    }

    @GetMapping
    public ResponseEntity<List<TaskViewDto>> getTasks(@ModelAttribute("ownerId") Long ownerId) {
        return ResponseEntity.ok(taskService.getTasksByUserId(ownerId));
    }

    @PostMapping
    public ResponseEntity<TaskViewDto> createTask(@Valid @RequestBody TaskCreateDto taskDto,
                                                  BindingResult bindingResult,
                                                  @ModelAttribute("ownerId") Long ownerId) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        taskDto.setOwnerId(ownerId);
        return ResponseEntity.ok(taskService.create(taskDto));
    }
}
