package com.backend.agrosensor.agrosensorbackend.controller.auth;


import com.backend.agrosensor.agrosensorbackend.service.auth.ClientRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/clients")
@RequiredArgsConstructor
public class ClientRegistrationController {

    private final ClientRegistrationService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registerClient(@RequestBody RegisterRequest request) {
        TokenResponse response = service.registerClient(request);
        return ResponseEntity.ok(response);
    }
}