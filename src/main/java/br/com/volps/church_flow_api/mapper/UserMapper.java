package br.com.volps.church_flow_api.mapper;

import br.com.volps.church_flow_api.dto.UserResponse;
import br.com.volps.church_flow_api.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        var response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setName(user.getName());
        return response;
    }
}
