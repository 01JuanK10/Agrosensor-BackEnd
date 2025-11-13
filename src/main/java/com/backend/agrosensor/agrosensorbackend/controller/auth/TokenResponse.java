package com.backend.agrosensor.agrosensorbackend.controller.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken,
        @JsonProperty("role")
        String role,
        @JsonProperty("name")
        String name,
        @JsonProperty("cc")
        Long cc
) {
}