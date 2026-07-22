package br.com.volps.church_flow_api.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.volps.church_flow_api.dto.LoginRequest;
import br.com.volps.church_flow_api.dto.LoginResponse;
import br.com.volps.church_flow_api.entity.User;
import br.com.volps.church_flow_api.exception.DomainException;
import br.com.volps.church_flow_api.exception.ErrorCode;
import br.com.volps.church_flow_api.mapper.UserMapper;
import br.com.volps.church_flow_api.repository.UserRepository;
import br.com.volps.church_flow_api.security.JwtService;

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

    public LoginResponse login(LoginRequest request) {

        User user = repository.findByEmail(request.email())
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND,
                        "User not found with email: " + request.email()));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new DomainException(ErrorCode.INVALID_CREDENTIALS, "Invalid credentials");
        }

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        return UserMapper.toResponse(token, "Bearer");
    }
}
