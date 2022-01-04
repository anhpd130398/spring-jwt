package com.example.springjwt.controller;

import com.example.springjwt.entities.User;
import com.example.springjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User userRegister(@RequestBody User user) {
        return null;
    }

}
