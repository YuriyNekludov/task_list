package yuriy.spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yuriy.spring.dto.TaskUpdateDto;
import yuriy.spring.dto.TaskViewDto;
import yuriy.spring.service.TaskService;
import yuriy.spring.util.TokenParser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks/{taskId:\\d+}")
public class TaskController {

    private final TaskService taskService;
    private final TokenParser tokenParser;

    @ModelAttribute("ownerId")
    public Long getOwnerId(@RequestHeader(value = "Authorization") String token) {
        return Long.valueOf(tokenParser.getOwnerIdFromToken(token));
    }

    @GetMapping
    public ResponseEntity<TaskViewDto> getTask(@PathVariable Long taskId,
                                               @ModelAttribute("ownerId") Long ownerId) {
        return ResponseEntity.ok(taskService.getTask(ownerId, taskId));
    }

    @PatchMapping
    public ResponseEntity<Void> updateTask(@Valid @RequestBody TaskUpdateDto taskDto,
                                           BindingResult bindingResult,
                                           @PathVariable Long taskId,
                                           @ModelAttribute("ownerId") Long ownerId) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        taskDto.setId(taskId);
        taskDto.setOwnerId(ownerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId,
                                           @ModelAttribute("ownerId") Long ownerId) {
        taskService.delete(taskId, ownerId);
        return ResponseEntity.noContent().build();
    }
}
