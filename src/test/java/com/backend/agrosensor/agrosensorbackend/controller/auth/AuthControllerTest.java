package com.backend.agrosensor.agrosensorbackend.controller.auth;

import com.backend.agrosensor.agrosensorbackend.service.auth.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void login_should_return_token_response() throws Exception {
        AuthRequest req = new AuthRequest("admin", "123");
        TokenResponse resp = new TokenResponse("token123", "refresh123", "ADMIN", "Admin", 100L);

        when(authService.authenticate(req)).thenReturn(resp);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username":"admin",
                                  "password":"123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("token123"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void register_should_return_token_response() throws Exception {
        RegisterRequest req = new RegisterRequest(10L, "John", "Doe", "a@b.com", "john", "pass", "DEVICE");
        TokenResponse resp = new TokenResponse("abc", "ref", "DEVICE", "John", 10L);

        when(authService.register(req)).thenReturn(resp);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "cc":10,
                                  "name":"John",
                                  "lastname":"Doe",
                                  "email":"a@b.com",
                                  "username":"john",
                                  "password":"pass",
                                  "role":"DEVICE"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("DEVICE"));
    }
}
