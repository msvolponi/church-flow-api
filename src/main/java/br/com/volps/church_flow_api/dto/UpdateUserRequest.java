package br.com.volps.church_flow_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(Long id, @NotBlank @Email @Size(max = 255) String email,
        @NotBlank @Size(max = 255) String name,
        @Size(max = 255) String password, @NotBlank @Size(max = 255) String role) {

}
