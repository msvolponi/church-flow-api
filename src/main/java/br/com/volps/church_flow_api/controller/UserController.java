package br.com.volps.church_flow_api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.volps.church_flow_api.dto.ApiResponse;
import br.com.volps.church_flow_api.dto.CreateUserRequest;
import br.com.volps.church_flow_api.dto.UpdateUserRequest;
import br.com.volps.church_flow_api.dto.UserResponse;
import br.com.volps.church_flow_api.entity.User;
import br.com.volps.church_flow_api.enums.Role;
import br.com.volps.church_flow_api.mapper.UserMapper;
import br.com.volps.church_flow_api.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<UserResponse>> register(@AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid CreateUserRequest request) {
        Role role = Role.valueOf(jwt.getClaimAsString("role"));
        User createdUser = userService.create(request, role);
        UserResponse response = UserMapper.toResponse(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(response));
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<UserResponse>> update(@AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid UpdateUserRequest request) {
        Role role = Role.valueOf(jwt.getClaimAsString("role"));
        userService.update(request, role);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.of(null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> list(@AuthenticationPrincipal Jwt jwt) {
        Role role = Role.valueOf(jwt.getClaimAsString("role"));
        List<UserResponse> users = userService.list(role).stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.of(users));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        Role role = Role.valueOf(jwt.getClaimAsString("role"));
        userService.delete(id, role);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.of(null));
    }
}
