package br.com.volps.church_flow_api.mapper;

import br.com.volps.church_flow_api.dto.CreateUserResponse;
import br.com.volps.church_flow_api.entity.User;

public class CreateUserMapper {

    public static CreateUserResponse toResponse(User user) {
        CreateUserResponse response = new CreateUserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setName(user.getName());
        return response;
    }
}
