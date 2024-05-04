package yuriy.spring.service;

import yuriy.spring.entity.User;

public interface UserService {

    User getByUsername(String username);

    User getById(Long id);

    User create(User user);
}
