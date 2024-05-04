package yuriy.spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yuriy.spring.dto.jwt_entity.JwtRequest;
import yuriy.spring.dto.jwt_entity.JwtResponse;
import yuriy.spring.dto.user.UserRegistrationDto;
import yuriy.spring.dto.user.UserViewDto;
import yuriy.spring.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/registration")
    public ResponseEntity<UserViewDto> register(@Valid @RequestBody UserRegistrationDto userDto,
                                                BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException ex) {
                throw ex;
            } else {
                throw new BindException(bindingResult);
            }
        }
        var user = authenticationService.register(userDto);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody String refreshToken) {
        return ResponseEntity.ok(authenticationService.refresh(refreshToken));
    }
}
