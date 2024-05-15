package yuriy.spring.client;

import yuriy.spring.entity.JwtResponse;
import yuriy.spring.entity.UserLoginDto;
import yuriy.spring.entity.UserRegistrationDto;
import yuriy.spring.entity.UserResponseDto;

public interface AuthenticationClient {

    JwtResponse doLogin(UserLoginDto userLoginDto);

    UserResponseDto doRegistration(UserRegistrationDto userRegistrationDto);

    void doLogout();
}
