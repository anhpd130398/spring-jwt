package demo.controller;

import demo.entities.BaseResponse;
import demo.entities.UserBO;
import demo.entities.payload.AuthRequest;
import demo.repository.UserRepository;
import demo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JwtController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String loginUser(@RequestBody AuthRequest request) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())

            );
            return jwtUtils.generateToken(request.getUsername());
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;

        }
    }

    @PostMapping("/register")
    public UserBO registerUser(@RequestBody AuthRequest request) {
        UserBO userBO = new UserBO();
        userBO.setUsername(request.getUsername());
        userBO.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(userBO);
    }
}
