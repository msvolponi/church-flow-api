package br.com.volps.church_flow_api.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.volps.church_flow_api.exception.DomainException;
import br.com.volps.church_flow_api.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey key;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long accessExpiration,
            @Value("${jwt.refresh-expiration}") long refreshExpiration) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String generateAccessToken(Long userId, String name, String email, String role) {
        return generateToken(userId, name, email, role, accessExpiration, "access");
    }

    public String generateRefreshToken(Long userId, String name, String email, String role) {
        return generateToken(userId, name, email, role, refreshExpiration, "refresh");
    }

    public Claims validateAccessToken(String token) {
        return validateToken(token, "access");
    }

    public Claims validateRefreshToken(String token) {
        return validateToken(token, "refresh");
    }

    private String generateToken(Long userId, String name, String email, String role, long expiration,
            String tokenType) {
        Date issuedAt = new Date();
        return Jwts.builder()
                .subject(userId.toString())
                .claim("name", name)
                .claim("role", role)
                .claim("email", email)
                .claim("token_type", tokenType)
                .issuedAt(issuedAt)
                .expiration(new Date(issuedAt.getTime() + expiration))
                .signWith(key)
                .compact();
    }

    private Claims validateToken(String token, String expectedType) {
        try {
            Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            Claims payload = claims.getPayload();

            if (!expectedType.equals(payload.get("token_type", String.class))) {
                throw new DomainException(ErrorCode.INVALID_CREDENTIALS, "Invalid token type");
            }

            return payload;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new DomainException(ErrorCode.INVALID_CREDENTIALS, "Invalid token");
        }
    }
}
