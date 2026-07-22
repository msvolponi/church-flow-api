package br.com.volps.church_flow_api.dto;

import java.time.Instant;

public record ApiResponse<T>(
        T data,
        Instant timestamp) {

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(
                data,
                Instant.now());
    }
}