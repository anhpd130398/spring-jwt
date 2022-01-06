package jwt.controller;

import jwt.entities.User;
import jwt.entities.payload.LoginRequest;
import jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class JwtController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public User saveInfo(@RequestBody LoginRequest loginRequest) {
        return userService.addUser(loginRequest);
    }
}
