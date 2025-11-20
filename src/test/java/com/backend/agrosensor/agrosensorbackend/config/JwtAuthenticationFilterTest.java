package com.backend.agrosensor.agrosensorbackend.config;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import com.backend.agrosensor.agrosensorbackend.repository.Auth.Token;
import com.backend.agrosensor.agrosensorbackend.repository.Auth.TokenRepository;
import com.backend.agrosensor.agrosensorbackend.repository.users.IUserRepository;
import com.backend.agrosensor.agrosensorbackend.service.auth.JwtService;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    JwtService jwtService;

    @Mock
    UserDetailsService userDetailsService;

    @Mock
    TokenRepository tokenRepository;

    @Mock
    IUserRepository userRepository;

    @Captor
    ArgumentCaptor<jakarta.servlet.ServletRequest> requestCaptor;

    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        filter = new JwtAuthenticationFilter(jwtService, userDetailsService, tokenRepository, userRepository);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_withoutAuthorizationHeader_shouldContinueChainAndNotAuthenticate() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/api/measurements/soil"); // secured path
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        // No Authorization header present
        filter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                "Authentication should be null when Authorization header is absent");
        // ensure response status not modified
        assertEquals(200, response.getStatus());
    }

    @Test
    void doFilterInternal_withValidToken_shouldSetAuthentication() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/api/measurements/soil");
        request.addHeader("Authorization", "Bearer valid-token-123");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        // Stubbing behavior
        when(jwtService.extractUsername("valid-token-123")).thenReturn("someuser");

        // tokenRepository returns a token entity that is NOT expired and NOT revoked
        Token tokenEntity = Token.builder()
                .token("valid-token-123")
                .isExpired(false)
                .isRevoked(false)
                .tokenType(Token.TokenType.BEARER)
                .build();
        // user will be present in repo
        AbstractUser repoUser = new AbstractUser();
        repoUser.setUsername("someuser");
        repoUser.setPassword("pwd");
        repoUser.setName("Some User");
        repoUser.setCc(9999L);

        when(tokenRepository.findByToken("valid-token-123")).thenReturn(Optional.of(tokenEntity));
        when(userRepository.findByUsername("someuser")).thenReturn(Optional.of(repoUser));
        // userDetailsService returns a Spring Security UserDetails object
        when(userDetailsService.loadUserByUsername("someuser"))
                .thenReturn(User.withUsername("someuser").password("pwd").authorities("ROLE_CLIENT").build());
        // jwtService.isTokenValid should say token valid for that user
        when(jwtService.isTokenValid("valid-token-123", repoUser)).thenReturn(true);

        filter.doFilterInternal(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication(),
                "Authentication must be set for a valid token");

        // verify that the authentication principal username equals expected
        assertEquals("someuser", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    void doFilterInternal_tokenPresent_butRevokedOrExpired_shouldNotAuthenticate() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/api/measurements/soil");
        request.addHeader("Authorization", "Bearer token-revoked");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        when(jwtService.extractUsername("token-revoked")).thenReturn("userx");
        // token exists but is expired or revoked -> repository returns token but flags true
        Token t = Token.builder()
                .token("token-revoked")
                .isExpired(true)
                .isRevoked(true)
                .tokenType(Token.TokenType.BEARER)
                .build();
        when(tokenRepository.findByToken("token-revoked")).thenReturn(Optional.of(t));

        filter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                "Authentication should remain null if token is expired or revoked");
    }
}
