package br.com.volps.church_flow_api.mapper;

import br.com.volps.church_flow_api.dto.LoginResponse;
import br.com.volps.church_flow_api.dto.UserResponse;
import br.com.volps.church_flow_api.entity.User;

public class UserMapper {

    public static LoginResponse toResponse(String token, String type, String refreshToken) {
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setType(type);
        response.setRefreshToken(refreshToken);
        return response;
    }

    public static UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setName(user.getName());
        return response;
    }
}
