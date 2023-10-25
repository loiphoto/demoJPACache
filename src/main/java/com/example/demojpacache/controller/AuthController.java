package com.example.demojpacache.controller;

import com.example.demojpacache.Entity.Role;
import com.example.demojpacache.Entity.User;
import com.example.demojpacache.dto.request.LoginRequest;
import com.example.demojpacache.dto.request.SignupRequest;
import com.example.demojpacache.dto.response.JwtResponse;
import com.example.demojpacache.exception.ErrorMessage;
import com.example.demojpacache.repository.RoleRepository;
import com.example.demojpacache.repository.UserRepository;
import com.example.demojpacache.service.base.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    PasswordEncoder encoder;


    public AuthController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorMessage(1, "Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorMessage(1, "Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail());

        String roleName = signUpRequest.getRoleName();
        Role userRole = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        user.setRole(userRole);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity getToken(@RequestParam String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
