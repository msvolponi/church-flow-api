package br.com.volps.church_flow_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(Long id, @NotBlank @Email String email, @NotBlank String name,
                String password, @NotBlank String role) {

}
