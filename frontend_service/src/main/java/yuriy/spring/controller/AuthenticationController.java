package yuriy.spring.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import yuriy.spring.client.AuthenticationClient;
import yuriy.spring.entity.UserLoginDto;
import yuriy.spring.entity.UserRegistrationDto;
import yuriy.spring.exception.RegistrationException;
import yuriy.spring.exception.UserLoginException;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationClient authenticationClient;

    @GetMapping("/login")
    public String getLoginPage() {
        return "authentication/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "authentication/registration";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute UserLoginDto userDto,
                          Model model,
                          HttpServletResponse resp) {
        try {
            var jwtResponse = authenticationClient.doLogin(userDto);
            setNewCookie("refreshToken",jwtResponse.refreshToken(), resp, 30 * 24 * 60 * 60);
            setNewCookie("accessToken", jwtResponse.accessToken(), resp, 600);
            setNewCookie("username", jwtResponse.username(), resp, 30 * 24 * 60 * 60);
        } catch (UserLoginException e) {
            model.addAttribute("error", e.getMessage());
            return "authentication/login";
        }
        return "redirect:/";
    }

    @PostMapping("/registration")
    public String doRegistration(@ModelAttribute UserRegistrationDto userDto,
                                 Model model) {
        try {
            authenticationClient.doRegistration(userDto);
        } catch (RegistrationException e) {
            model.addAttribute("errors", e.getErrors());
            return "authentication/registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    private void setNewCookie(String name,
                              String value,
                              HttpServletResponse response,
                              int aliveTime) {
        var cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(aliveTime);
        response.addCookie(cookie);
    }
}
