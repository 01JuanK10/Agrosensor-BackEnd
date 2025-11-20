package com.backend.agrosensor.agrosensorbackend.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Admin;

class JwtServiceTest {

    private JwtService jwtService;

    /**
     * NOTE: the secretKey below is a BASE64 encoded random string.
     * In a real test you can generate it programmatically or reuse a test constant.
     */
    private static final String TEST_SECRET_BASE64 = "ZmFrZXNlY3JldGtleWZvcndlYjEyMzQ1Njc4OTAxMjM0NTY3ODkwMTI=";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // configure private fields via ReflectionTestUtils
        ReflectionTestUtils.setField(jwtService, "secretKey", TEST_SECRET_BASE64);
        // 1 hour
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600_000L);
        // 2 hours
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", 7_200_000L);
    }

    @Test
    void generateToken_and_extractUsername_and_isTokenValid_shouldWork() {
        Admin user = new Admin();
        user.setUsername("testuser");
        user.setName("Test");
        user.setCc(12345L);

        String token = jwtService.generateToken(user);
        assertNotNull(token, "Token should not be null");

        String extracted = jwtService.extractUsername(token);
        assertEquals(user.getUsername(), extracted, "Extracted username should match");

        boolean valid = jwtService.isTokenValid(token, user);
        assertTrue(valid, "Token should be valid immediately after generation");
    }

    @Test
    void token_should_become_invalid_after_expiration() throws InterruptedException {
        // expiration very short (1ms)
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1L);

        Admin user = new Admin();
        user.setUsername("expireUser");
        user.setName("Expire");
        user.setCc(111L);

        String token = jwtService.generateToken(user);
        assertNotNull(token);

        // Wait long enough for expiration
        Thread.sleep(10);

        // The JWT library will throw ExpiredJwtException when parsing an expired token
        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> {
            jwtService.isTokenValid(token, user);
        });
    }


    @Test
    void generateRefreshToken_shouldProduceDifferentToken() {
        Admin user = new Admin();
        user.setUsername("refreshUser");
        user.setName("Refresh");
        user.setCc(222L);

        String access = jwtService.generateToken(user);
        String refresh = jwtService.generateRefreshToken(user);

        assertNotNull(access);
        assertNotNull(refresh);
        assertNotEquals(access, refresh, "Access token and refresh token should not be equal");
    }
}
