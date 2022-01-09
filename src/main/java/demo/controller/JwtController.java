package demo.controller;

import demo.entities.BaseResponse;
import demo.entities.UserBO;
import demo.entities.payload.AuthRequest;
import demo.entities.payload.AuthResponse;
import demo.repository.UserRepository;
import demo.security.JwtUtils;
import demo.service.CustomerDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    private CustomerDetailsService detailsService;

    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        UserDetails userDetails = detailsService.loadUserByUsername(authRequest.getUsername());
        String jwtToken = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.doGenerateRefreshToken(userDetails);
        String userName = jwtUtils.extractUsername(jwtToken);
        UserBO userB1 = userRepository.findByUserName(userName);
        userB1.setToken(jwtToken);
        userB1.setRefreshToken(refreshToken);
        userRepository.save(userB1);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtToken);
        authResponse.setRefreshToken(refreshToken);
        return authResponse;
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestParam("refreshToken") String refreshToken) throws SecurityException, IOException {
        UserBO userBO = userRepository.findByRefreshToken(refreshToken);

        try {
            if (userBO != null) {
                UserDetails userDetails = detailsService.loadUserByUsername(userBO.getRefreshToken());
                Boolean check = jwtUtils.validateToken(userBO.getToken(), userDetails);
                return ResponseEntity.ok("tokens are still valid" + check);
            } else {
                return ResponseEntity.badRequest().body("refreshToken incorrect or not expire");
            }
        } catch (ExpiredJwtException ex) {
            UserDetails userDetails = detailsService.loadUserByUsername(userBO.getRefreshToken());
            String token = jwtUtils.generateToken(userDetails);
            userBO.setToken(token);
            userRepository.save(userBO);
            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(token);
            authResponse.setRefreshToken(refreshToken);
            return ResponseEntity.ok(authResponse);

        }
    }

    @PostMapping("/register")
    public UserBO registerUser(@RequestBody AuthRequest request) {
        UserBO userBO = new UserBO();
        userBO.setUsername(request.getUsername());
        userBO.setPassword(passwordEncoder.encode(request.getPassword()));
        userBO.setRoleId(request.getRoleId());
        return userRepository.save(userBO);
    }


    @GetMapping("/message")
    public List<UserBO> message() throws SecurityException, Exception {
        return userRepository.findAll();
    }
}
