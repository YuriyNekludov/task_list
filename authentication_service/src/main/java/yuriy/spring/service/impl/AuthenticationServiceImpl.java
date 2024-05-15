package yuriy.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yuriy.spring.dto.jwt_entity.JwtRequest;
import yuriy.spring.dto.jwt_entity.JwtResponse;
import yuriy.spring.dto.user.UserRegistrationDto;
import yuriy.spring.dto.user.UserViewDto;
import yuriy.spring.exception.PasswordsMismatchingException;
import yuriy.spring.mapper.UserMapper;
import yuriy.spring.security.JwtTokenProvider;
import yuriy.spring.service.AuthenticationService;
import yuriy.spring.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public JwtResponse login(JwtRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        var user = userService.getByUsername(loginRequest.username());
        return JwtResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .accessToken(jwtTokenProvider.createAccessToken(user))
                .refreshToken(jwtTokenProvider.createRefreshToken(user))
                .build();
    }

    @Override
    public UserViewDto register(UserRegistrationDto userDto) {
        if (arePasswordsMismatching(userDto)) {
            throw new PasswordsMismatchingException("authentication.errors.passwords.mismatching");
        }
        var user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userService.create(user));
    }

    @Override
    public JwtResponse refresh(String token) {
        return jwtTokenProvider.refreshUserAccessToken(token);
    }

    private boolean arePasswordsMismatching(UserRegistrationDto userDto) {
        return !userDto.password().equals(userDto.passwordConfirmation());
    }
}
