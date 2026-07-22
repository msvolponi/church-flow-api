package br.com.volps.church_flow_api.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "The requested resource was not found"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation error occurred"),
    BUSINESS_ERROR(HttpStatus.CONFLICT, "Business error occurred"),
    ALREADY_USED(HttpStatus.CONFLICT, "Resource is already in use"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid credentials"),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "Invalid value specified"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error occurred");

    private final HttpStatus status;
    private final String description;

    ErrorCode(HttpStatus status, String description) {
        this.status = status;
        this.description = description;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}