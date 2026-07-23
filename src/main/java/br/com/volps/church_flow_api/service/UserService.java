package br.com.volps.church_flow_api.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.volps.church_flow_api.dto.CreateUserRequest;
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
        repository.findByEmail(request.email()).ifPresent(existing -> {
            throw new DomainException(ErrorCode.ALREADY_USED, "Email already in use: " +
                    existing.getEmail());
        });

        Role role;
        try {
            role = Role.valueOf(request.role());
        } catch (IllegalArgumentException e) {
            throw new DomainException(ErrorCode.INVALID_VALUE, "Invalid role: " + request.role());
        }

        if (currentRole.equals(Role.MANAGER) && role.equals(Role.ADMIN)) {
            throw new DomainException(ErrorCode.FORBIDDEN, "Managers cannot create Admin users");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setName(request.name());
        user.setRole(role);
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        return repository.save(user);
    }
}
