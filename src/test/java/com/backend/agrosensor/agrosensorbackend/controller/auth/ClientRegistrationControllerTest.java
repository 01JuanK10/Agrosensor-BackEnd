package com.backend.agrosensor.agrosensorbackend.controller.auth;

import com.backend.agrosensor.agrosensorbackend.service.auth.ClientRegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRegistrationService service;

    @Test
    @WithMockUser(roles = "ADMIN")
    void admin_can_register_client() throws Exception {
        RegisterRequest req = new RegisterRequest(
                20L, "Ana", "Lopez", "ana@gmail.com", "ana", "12345", "CLIENT"
        );

        TokenResponse resp = new TokenResponse("tok", "ref", "CLIENT", "Ana Lopez", 20L);

        when(service.registerClient(req)).thenReturn(resp);

        mockMvc.perform(post("/admin/clients/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "cc":20,
                                  "name":"Ana",
                                  "lastname":"Lopez",
                                  "email":"ana@gmail.com",
                                  "username":"ana",
                                  "password":"12345",
                                  "role":"CLIENT"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("CLIENT"));
    }
}
