package yuriy.spring.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import yuriy.spring.entity.Authority;
import yuriy.spring.entity.User;

import java.util.List;

public class JwtEntityFactory {

    public static JwtEntity create(User user) {
        return JwtEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .authorities(mapToGrantedAuthority(user.getAuthorities()))
                .build();
    }

    private static List<? extends GrantedAuthority> mapToGrantedAuthority(List<Authority> authorities) {
        return authorities.stream()
                .map(Authority::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast)
                .toList();
    }
}
