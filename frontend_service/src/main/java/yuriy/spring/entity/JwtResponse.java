package yuriy.spring.entity;

public record JwtResponse(Long id,
                          String username,
                          String email,
                          String accessToken,
                          String refreshToken) {
}
