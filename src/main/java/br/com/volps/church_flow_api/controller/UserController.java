package br.com.volps.church_flow_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.volps.church_flow_api.dto.ApiResponse;
import br.com.volps.church_flow_api.dto.CreateUserRequest;
import br.com.volps.church_flow_api.dto.CreateUserResponse;
import br.com.volps.church_flow_api.entity.User;
import br.com.volps.church_flow_api.mapper.CreateUserMapper;
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
    public ResponseEntity<ApiResponse<CreateUserResponse>> register(@RequestBody @Valid CreateUserRequest request) {
        User createdUser = userService.create(request);
        CreateUserResponse response = CreateUserMapper.toResponse(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(response));
    }
}
