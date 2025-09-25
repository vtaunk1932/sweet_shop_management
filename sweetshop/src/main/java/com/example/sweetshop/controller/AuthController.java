package com.example.sweetshop.controller;

import com.example.sweetshop.dtos.LoginRequest;
import com.example.sweetshop.entity.User;
import com.example.sweetshop.security.JwtUtil;
import com.example.sweetshop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // No password encoding, just storing raw password
            User savedUser = userService.registerUser(user);
            return ResponseEntity.ok(savedUser); // returning user with plain password
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // If user not found → NullPointerException
            User user = userService.findByEmail(loginRequest.getEmail());

            // Plain text password check (insecure)
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.status(401).body("Invalid credentials");
            }

            // Returning raw token string (not JSON object)
            String token = jwtUtil.generateToken(user);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/test")
    public String test() {
        return "Auth controller is working!";
    }
}
