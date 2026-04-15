package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // SIGNUP API
    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return userRepository.save(user);
    }

    // LOGIN API
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return "SUCCESS";
        } else {
            return "FAIL";
        }
    }

    // GET ALL USERS (for testing)
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}