package demo.controller;

import demo.entities.BaseResponse;
import demo.entities.UserBO;
import demo.entities.payload.AuthRequest;
import demo.repository.UserRepository;
import demo.security.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
public class JwtController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String loginUser(@RequestBody UserBO userBO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userBO.getUsername(), userBO.getPassword()));

        return "token: " + jwtUtils.generateToken(userBO.getUsername());
    }

    @PostMapping("/register")
    public UserBO registerUser(@RequestBody AuthRequest request) {
        UserBO userBO = new UserBO();
        userBO.setUsername(request.getUsername());
        userBO.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(userBO);
    }


    @GetMapping("/message")
    public List<UserBO> message() throws SecurityException, Exception {
        return userRepository.findAll();
    }
}
