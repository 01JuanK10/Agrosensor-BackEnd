package com.backend.agrosensor.agrosensorbackend.service.auth;

import com.backend.agrosensor.agrosensorbackend.controller.auth.AuthRequest;
import com.backend.agrosensor.agrosensorbackend.controller.auth.RegisterRequest;
import com.backend.agrosensor.agrosensorbackend.controller.auth.TokenResponse;
import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import com.backend.agrosensor.agrosensorbackend.repository.Auth.Token;
import com.backend.agrosensor.agrosensorbackend.repository.Auth.TokenRepository;
import com.backend.agrosensor.agrosensorbackend.repository.users.IUserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IUserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(final RegisterRequest request) {
        final AbstractUser user = AbstractUser.builder()
                .cc(request.cc())
                .name(request.name())
                .lastname(request.lastname())
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();

        AbstractUser savedUser;

        switch (request.role()) {
            case "DEVICE":
                savedUser = repository.save(user);
                break;
            default:
                throw new IllegalArgumentException("Invalid role");
        }

        final String jwtToken = jwtService.generateToken(savedUser);
        final String refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken, savedUser.getRole(), savedUser.getUsername(), savedUser.getCc());
    }


    public TokenResponse authenticate(final AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        final AbstractUser user = repository.findByUsername(request.username())
                .orElseThrow();
        final String accessToken = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponse(accessToken, refreshToken, user.getRole(), user.getName() + " " + user.getLastname(), user.getCc());
    }

    private void saveUserToken(AbstractUser user, String jwtToken) {
        final Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final AbstractUser user) {
        final List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getCc()));
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setIsExpired(true);
                token.setIsRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponse refreshToken(@NotNull final String authentication) {

        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }
        final String refreshToken = authentication.substring(7);
        final String username = jwtService.extractUsername(refreshToken);
        if (username == null) {
            return null;
        }

        final AbstractUser user = this.repository.findByUsername(username).orElseThrow();
        final boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);
        if (!isTokenValid) {
            return null;
        }

        final String accessToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new TokenResponse(accessToken, refreshToken, user.getRole(), user.getUsername() + " " + user.getLastname(), user.getCc());
    }

    public String generatePasswordResetToken(AbstractUser user) {
        String token = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, token);
        return token;
    }

}
