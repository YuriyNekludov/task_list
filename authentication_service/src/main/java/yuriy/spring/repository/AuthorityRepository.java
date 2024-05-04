package yuriy.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yuriy.spring.entity.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByAuthority(String authority);
}
