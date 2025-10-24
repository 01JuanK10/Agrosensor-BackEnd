package com.backend.agrosensor.agrosensorbackend.controller.auth;

public record AuthRequest(
        String username,
        String password
){}