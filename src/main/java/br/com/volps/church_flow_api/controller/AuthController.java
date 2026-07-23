package br.com.volps.church_flow_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.volps.church_flow_api.dto.LoginRequest;
import br.com.volps.church_flow_api.dto.LoginResponse;
import br.com.volps.church_flow_api.dto.RefreshTokenRequest;
import br.com.volps.church_flow_api.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        LoginResponse response = authService.refreshToken(request.refreshToken());
        return ResponseEntity.ok(response);
    }
}