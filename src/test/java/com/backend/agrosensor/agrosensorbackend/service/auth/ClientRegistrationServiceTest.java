package com.backend.agrosensor.agrosensorbackend.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.agrosensor.agrosensorbackend.controller.auth.RegisterRequest;
import com.backend.agrosensor.agrosensorbackend.controller.auth.TokenResponse;
import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.repository.users.IClientRepository;

@SpringBootTest
class ClientRegistrationServiceTest {

    @Mock
    private IClientRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private ClientRegistrationService service;

    @Test
    void registerClient_should_create_client() {
        RegisterRequest req = new RegisterRequest(
                1L, "Ana", "Lopez", "ana@mail.com", "ana", "123", null
        );

        Client saved = new Client();
        saved.setCc(1L);
        saved.setName("Ana");
        saved.setLastname("Lopez");
        saved.setRole("CLIENT");

        when(encoder.encode("123")).thenReturn("encoded123");
        when(repository.save(any())).thenReturn(saved);
        when(jwtService.generateToken(saved)).thenReturn("tk");
        when(jwtService.generateRefreshToken(saved)).thenReturn("rtk");

        TokenResponse response = service.registerClient(req);

        assertEquals("CLIENT", response.role());
        assertEquals("tk", response.accessToken());
    }
}
