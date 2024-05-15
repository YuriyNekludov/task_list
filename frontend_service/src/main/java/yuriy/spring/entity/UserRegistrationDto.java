package yuriy.spring.entity;

public record UserRegistrationDto(String email,
                                  String username,
                                  String password,
                                  String passwordConfirmation) {
}
