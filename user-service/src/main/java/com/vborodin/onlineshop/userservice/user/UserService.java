package com.vborodin.onlineshop.userservice.user;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Iterable<User> findAll() {
        return userRepository.findAll();
    }

    User save(User user) {
        return userRepository.save(user);
    }

    Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
