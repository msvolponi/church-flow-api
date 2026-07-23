package br.com.volps.church_flow_api.mapper;

import br.com.volps.church_flow_api.dto.LoginResponse;

public class UserMapper {

    public static LoginResponse toResponse(String token, String type, String refreshToken) {
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setType(type);
        response.setRefreshToken(refreshToken);
        return response;
    }
}
