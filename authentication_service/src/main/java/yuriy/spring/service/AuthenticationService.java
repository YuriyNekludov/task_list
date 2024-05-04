package yuriy.spring.service;

import yuriy.spring.dto.jwt_entity.JwtRequest;
import yuriy.spring.dto.jwt_entity.JwtResponse;
import yuriy.spring.dto.user.UserRegistrationDto;
import yuriy.spring.dto.user.UserViewDto;

public interface AuthenticationService {

    JwtResponse login(JwtRequest loginRequest);

    UserViewDto register(UserRegistrationDto userDto);

    JwtResponse refresh(String token);
}
