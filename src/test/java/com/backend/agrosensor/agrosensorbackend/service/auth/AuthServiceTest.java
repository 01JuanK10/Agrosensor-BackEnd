package com.backend.agrosensor.agrosensorbackend.service.auth;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.agrosensor.agrosensorbackend.controller.auth.AuthRequest;
import com.backend.agrosensor.agrosensorbackend.controller.auth.RegisterRequest;
import com.backend.agrosensor.agrosensorbackend.controller.auth.TokenResponse;
import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import com.backend.agrosensor.agrosensorbackend.repository.Auth.TokenRepository;
import com.backend.agrosensor.agrosensorbackend.repository.users.IUserRepository;

@SpringBootTest
class AuthServiceTest {

    @Mock
    private IUserRepository repository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void authenticate_should_return_tokens() {
        AuthRequest request = new AuthRequest("john", "123");
        AbstractUser user = AbstractUser.builder()
                .username("john")
                .name("John")
                .lastname("Doe")
                .cc(1L)
                .role("CLIENT")
                .build();

        when(repository.findByUsername("john")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("access123");
        when(jwtService.generateRefreshToken(user)).thenReturn("refresh123");

        TokenResponse response = authService.authenticate(request);

        assertEquals("access123", response.accessToken());
        assertEquals("refresh123", response.refreshToken());
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("john", "123")
        );
    }

    @Test
    void register_device_should_create_user() {
        RegisterRequest request = new RegisterRequest(
                111L, "dev", "device", "mail@mail.com",
                "device1", "pass", "DEVICE"
        );

        AbstractUser saved = AbstractUser.builder()
                .cc(111L)
                .username("device1")
                .role("DEVICE")
                .build();

        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(repository.save(any())).thenReturn(saved);
        when(jwtService.generateToken(saved)).thenReturn("tokenX");
        when(jwtService.generateRefreshToken(saved)).thenReturn("refreshX");

        TokenResponse response = authService.register(request);

        assertEquals("DEVICE", response.role());
        assertEquals("tokenX", response.accessToken());
    }
}
