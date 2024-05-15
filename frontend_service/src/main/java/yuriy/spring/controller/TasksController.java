package yuriy.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import yuriy.spring.client.TaskClient;
import yuriy.spring.entity.UserViewDto;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class TasksController {

    private final TaskClient taskClient;

    @ModelAttribute("userInfo")
    public UserViewDto getUserInfo(@CookieValue("username") String username) {
        return new UserViewDto(username);
    }

    @GetMapping
    public String getTasks(@CookieValue(value = "accessToken", required = false) String accessToken,
                           Model model) {
        model.addAttribute("tasks", taskClient.getTasks(accessToken));
        return "tasks/list";
    }
}
