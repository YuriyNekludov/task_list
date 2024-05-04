package yuriy.spring.dto.jwt_entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private Long id;
    private String username;
    private String email;
    private String accessToken;
    private String refreshToken;
}
