package com.example.sweetshop.controller;

import com.example.sweetshop.security.JwtUtil;
import com.example.sweetshop.service.UserService;
import com.example.sweetshop.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testRegisterUser() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        User savedUser = new User("John", "john@example.com", "1234567890", false);

        // Mocking the service to return the user
        when(userService.registerUser(any(User.class))).thenReturn(savedUser);

        String registerJson = """
                {
                    "name": "John",
                    "email": "john@example.com",
                    "password": "1234567890"
                }
                """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginUser_Success() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        User user = new User("John", "john@example.com", "pass", false);
        when(userService.findByEmail("john@example.com")).thenReturn(user);
        when(jwtUtil.generateToken(user)).thenReturn("token");

        String loginJson = """
                {"email":"john@example.com","password":"pass"}
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginUser_Failure() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        when(userService.findByEmail("invalid@example.com"))
                .thenThrow(new RuntimeException("User not found"));

        String loginJson = """
                {"email":"invalid@example.com","password":"pass"}
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isUnauthorized());
    }
}
