package code.service;

import code.model.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
