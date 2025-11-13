package com.backend.agrosensor.agrosensorbackend.service.auth;

import com.backend.agrosensor.agrosensorbackend.controller.auth.RegisterRequest;
import com.backend.agrosensor.agrosensorbackend.controller.auth.TokenResponse;
import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.repository.users.IClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientRegistrationService {

    private final IClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public TokenResponse registerClient(final RegisterRequest request) {
        Client client = new Client();
        client.setCc(request.cc());
        client.setName(request.name());
        client.setLastname(request.lastname());
        client.setUsername(request.username());
        client.setPassword(passwordEncoder.encode(request.password()));
        client.setRole("CLIENT");

        Client savedClient = clientRepository.save(client);

        final String jwtToken = jwtService.generateToken(savedClient);
        final String refreshToken = jwtService.generateRefreshToken(savedClient);

        return new TokenResponse(jwtToken, refreshToken, client.getRole(), client.getName() + " " + client.getLastname(), client.getCc());
    }
}