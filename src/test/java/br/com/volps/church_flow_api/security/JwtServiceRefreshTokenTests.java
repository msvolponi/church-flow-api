package br.com.volps.church_flow_api.security;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class JwtServiceRefreshTokenTests {

    @Test
    void shouldGenerateDistinctAccessAndRefreshTokens() {
        JwtService jwtService = new JwtService("8fK3mP9xQ2vL7sN4aR6tY1uW5zC0dE9h", 3_600_000L, 86_400_000L);

        String accessToken = jwtService.generateAccessToken(
                1L,
                "John Doe",
                "john@email.com",
                "ADMIN");

        String refreshToken = jwtService.generateRefreshToken(
                1L,
                "John Doe",
                "john@email.com",
                "ADMIN");

        assertNotNull(accessToken);
        assertNotNull(refreshToken);
        assertNotEquals(accessToken, refreshToken);
    }
}
