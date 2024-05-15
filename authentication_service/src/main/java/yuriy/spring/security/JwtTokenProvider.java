package yuriy.spring.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yuriy.spring.config.prop.JwtProperties;
import yuriy.spring.dto.jwt_entity.JwtResponse;
import yuriy.spring.entity.Authority;
import yuriy.spring.entity.User;
import yuriy.spring.service.UserService;
import yuriy.spring.util.TokenParser;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(User user) {
        var validity = Instant.now().plus(Long.parseLong(jwtProperties.getAccess()), ChronoUnit.MINUTES);
        return Jwts.builder()
                .claims()
                .subject(user.getUsername())
                .add("id", user.getId())
                .add("email", user.getEmail())
                .add("roles", user.getAuthorities()
                        .stream()
                        .map(Authority::getAuthority)
                        .toList())
                .and()
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(User user) {
        var validity = Instant.now().plus(Long.parseLong(jwtProperties.getRefresh()), ChronoUnit.DAYS);
        return Jwts.builder()
                .claims()
                .subject(user.getUsername())
                .add("id", user.getId())
                .and()
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserAccessToken(String refreshToken) {
        if (isInvalidToken(refreshToken)) {
            throw new AccessDeniedException("authentication.errors.token.refresh_token_invalid");
        }
        var userId = Long.valueOf(TokenParser.getUserIdFromToken(refreshToken, key));
        var user = userService.getById(userId);
        return JwtResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .accessToken(createAccessToken(user))
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    public Authentication getAuthentication(String token) {
        var username = TokenParser.getUsernameFromToken(token, key);
        var userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean isInvalidToken(String token) {
        return !Jwts
                .parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(Date.from(Instant.now()));
    }
}
