package br.com.volps.church_flow_api.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.volps.church_flow_api.dto.AuthRequest;
import br.com.volps.church_flow_api.dto.AuthResponse;
import br.com.volps.church_flow_api.entity.User;
import br.com.volps.church_flow_api.exception.DomainException;
import br.com.volps.church_flow_api.exception.ErrorCode;
import br.com.volps.church_flow_api.mapper.AuthMapper;
import br.com.volps.church_flow_api.repository.UserRepository;
import br.com.volps.church_flow_api.security.JwtService;
import io.jsonwebtoken.Claims;

@Service
public class AuthService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository repository, JwtService jwtService, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(AuthRequest request) {

        User user = repository.findByEmail(request.email())
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND,
                        "User not found with email: " + request.email()));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new DomainException(ErrorCode.INVALID_CREDENTIALS, "Invalid credentials");
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getName(), user.getEmail(), user.getRole().name());
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getName(), user.getEmail(), user.getRole().name());

        return AuthMapper.toResponse(accessToken, "Bearer", refreshToken);
    }

    public AuthResponse refreshToken(String refreshToken) {
        Claims claims = jwtService.validateRefreshToken(refreshToken);

        User user = repository.findByEmail(claims.get("email", String.class))
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND,
                        "User not found with email: " + claims.get("email", String.class)));

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getName(), user.getEmail(), user.getRole().name());
        String newRefreshToken = jwtService.generateRefreshToken(user.getId(), user.getName(), user.getEmail(), user.getRole().name());

        return AuthMapper.toResponse(accessToken, "Bearer", newRefreshToken);
    }
}
