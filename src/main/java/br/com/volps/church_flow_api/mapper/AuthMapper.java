package br.com.volps.church_flow_api.mapper;

import br.com.volps.church_flow_api.dto.AuthResponse;

public class AuthMapper {

    public static AuthResponse toResponse(String token, String type, String refreshToken) {
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setType(type);
        response.setRefreshToken(refreshToken);
        return response;
    }
}
