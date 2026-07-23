package br.com.volps.church_flow_api.security;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class JwtServiceRefreshTokenTests {

    @Test
    void shouldGenerateDistinctAccessAndRefreshTokens() {
        JwtService jwtService = new JwtService("8fK3mP9xQ2vL7sN4aR6tY1uW5zC0dE9h", 3_600_000L, 86_400_000L);

        String accessToken = jwtService.generateAccessToken(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "John Doe",
                "john@email.com",
                "ADMIN");

        String refreshToken = jwtService.generateRefreshToken(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "John Doe",
                "john@email.com",
                "ADMIN");

        assertNotNull(accessToken);
        assertNotNull(refreshToken);
        assertNotEquals(accessToken, refreshToken);
    }
}
