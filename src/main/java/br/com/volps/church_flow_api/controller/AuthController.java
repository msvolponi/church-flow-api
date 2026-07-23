package br.com.volps.church_flow_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.volps.church_flow_api.dto.LoginRequest;
import br.com.volps.church_flow_api.dto.LoginResponse;
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
        // response.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
        return ResponseEntity.ok(response);
    }
}