package com.multiversebackend.snipprio.controller;

import com.multiversebackend.snipprio.model.User;
import com.multiversebackend.snipprio.repository.UserRepository;
import com.multiversebackend.snipprio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public String addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/authenticateUsers")
    public String authenticateUser(@RequestBody User user) {
        return userService.authenticateUser(user);
    }

}
