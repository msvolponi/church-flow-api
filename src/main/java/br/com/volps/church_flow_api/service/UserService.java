package br.com.volps.church_flow_api.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.volps.church_flow_api.dto.CreateUserRequest;
import br.com.volps.church_flow_api.dto.UpdateUserRequest;
import br.com.volps.church_flow_api.entity.User;
import br.com.volps.church_flow_api.enums.Role;
import br.com.volps.church_flow_api.exception.DomainException;
import br.com.volps.church_flow_api.exception.ErrorCode;
import br.com.volps.church_flow_api.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(CreateUserRequest request, Role currentRole) {
        validateManagementPermission(currentRole);

        Role role = parseRole(request.role());

        if (role == Role.ADMIN && currentRole != Role.ADMIN) {
            throw new DomainException(ErrorCode.FORBIDDEN, "Only Admins can create Admin users");
        }

        validateEmail(request.email(), null);

        User user = new User();
        user.setEmail(request.email());
        user.setName(request.name());
        user.setRole(role);
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        return repository.save(user);
    }

    public User update(UpdateUserRequest request, Role currentRole) {
        validateManagementPermission(currentRole);

        User user = repository.findById(request.id())
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND, "User not found with id: " + request.id()));

        Role newRole = parseRole(request.role());

        if (user.getRole() == Role.ADMIN && currentRole != Role.ADMIN) {
            throw new DomainException(ErrorCode.FORBIDDEN, "Only Admins can update Admin users");
        }

        if (newRole == Role.ADMIN && currentRole != Role.ADMIN) {
            throw new DomainException(ErrorCode.FORBIDDEN, "Only Admins can assign Admin role");
        }

        validateEmail(request.email(), user.getId());

        if (request.password() != null && !request.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        user.setEmail(request.email());
        user.setName(request.name());
        user.setRole(newRole);

        return repository.save(user);
    }

    public List<User> list(Role currentRole) {
        switch (currentRole) {
            case ADMIN:
                return repository.findAll();
            case MANAGER:
                return repository.findByRoleNot(Role.ADMIN);
            case OPERATOR:
                throw new DomainException(ErrorCode.FORBIDDEN, "Operators cannot list users");
        }

        return null;
    }

    public void delete(Long id, Role currentRole) {
        validateManagementPermission(currentRole);

        User user = repository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorCode.NOT_FOUND, "User not found with id: " + id));

        if (user.getRole() == Role.ADMIN && currentRole != Role.ADMIN) {
            throw new DomainException(ErrorCode.FORBIDDEN, "Only Admins can delete Admin users");
        }

        repository.delete(user);
    }

    private void validateManagementPermission(Role currentRole) {
        if (currentRole != Role.ADMIN && currentRole != Role.MANAGER) {
            throw new DomainException(ErrorCode.FORBIDDEN, "Only Admins and Managers can manage users");
        }
    }

    private Role parseRole(String role) {
        try {
            return Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new DomainException(ErrorCode.INVALID_VALUE, "Invalid role: " + role);
        }
    }

    private void validateEmail(String email, Long currentUserId) {
        repository.findByEmail(email).ifPresent(existing -> {
            if (currentUserId == null || !existing.getId().equals(currentUserId)) {
                throw new DomainException(ErrorCode.ALREADY_USED, "Email already in use: " + email);
            }
        });
    }
}