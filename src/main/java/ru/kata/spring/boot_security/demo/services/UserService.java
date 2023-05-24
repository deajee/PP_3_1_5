package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User saveUser(User user);
    void deleteById(Long id);
    List<User> findAll();
    User findById(Long id);
}
