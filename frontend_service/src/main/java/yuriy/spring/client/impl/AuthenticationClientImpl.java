package yuriy.spring.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import yuriy.spring.client.AuthenticationClient;
import yuriy.spring.entity.JwtResponse;
import yuriy.spring.entity.UserLoginDto;
import yuriy.spring.entity.UserRegistrationDto;
import yuriy.spring.entity.UserResponseDto;
import yuriy.spring.exception.RegistrationException;
import yuriy.spring.exception.UserLoginException;

import java.util.List;

@RequiredArgsConstructor
public class AuthenticationClientImpl implements AuthenticationClient {

    private final RestClient restClient;

    @Override
    public JwtResponse doLogin(UserLoginDto userLoginDto) {
        try {
            return restClient.post()
                    .uri("/api/v1/auth/login")
                    .body(userLoginDto)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(JwtResponse.class);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new UserLoginException(e.getMessage());
        }
    }

    @Override
    public UserResponseDto doRegistration(UserRegistrationDto userRegistrationDto) {
        try {
            return restClient.post()
                    .uri("/api/v1/auth/registration")
                    .body(userRegistrationDto)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(UserResponseDto.class);
        } catch (HttpClientErrorException.BadRequest e) {
            var problemDetail = e.getResponseBodyAs(ProblemDetail.class);
            throw new RegistrationException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void doLogout() {
        restClient.get()
                .uri("/api/v1/auth/logout")
                .retrieve()
                .toBodilessEntity();
    }
}
