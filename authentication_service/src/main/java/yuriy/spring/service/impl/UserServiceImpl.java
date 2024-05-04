package yuriy.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yuriy.spring.entity.User;
import yuriy.spring.exception.AuthorityNotFoundException;
import yuriy.spring.exception.DuplicateUserAttributesException;
import yuriy.spring.repository.AuthorityRepository;
import yuriy.spring.repository.UserRepository;
import yuriy.spring.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private static final String DEFAULT_AUTHORITY = "ROLE_USER";

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("authentication.errors.user.not_found"));
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("authentication.errors.user.not_found"));
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateUserAttributesException("authentication.errors.user.duplicate_username");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateUserAttributesException("authentication.errors.user.duplicate_email");
        }
        var authority = authorityRepository.findByAuthority(DEFAULT_AUTHORITY)
                .orElseThrow(() -> new AuthorityNotFoundException("authentication.errors.authority.not_found"));
        user.getAuthorities().add(authority);
        return userRepository.save(user);
    }
}
