package yuriy.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yuriy.spring.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
}
