package com.backend.agrosensor.agrosensorbackend.controller.auth;

public record RegisterRequest(
        Long cc,
        String name,
        String lastname,
        String email,
        String username,
        String password,
        String role
) {
}