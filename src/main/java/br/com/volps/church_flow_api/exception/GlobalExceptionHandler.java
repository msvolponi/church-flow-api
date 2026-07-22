package br.com.volps.church_flow_api.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(HandlerMethodValidationException.class)
        public ResponseEntity<ProblemDetail> handleValidation(
                        HandlerMethodValidationException ex,
                        HttpServletRequest request) {

                List<Map<String, String>> errors = new ArrayList<>();

                ex.getParameterValidationResults().forEach(result -> {

                        String field = result.getMethodParameter().getParameterName();

                        result.getResolvableErrors().forEach(error -> {

                                Map<String, String> err = new HashMap<>();
                                err.put("field", field);
                                err.put("message", error.getDefaultMessage());

                                errors.add(err);
                        });
                });

                return ResponseEntity
                                .badRequest()
                                .body(ProblemFactory.create(
                                                HttpStatus.BAD_REQUEST,
                                                ErrorCode.VALIDATION_ERROR,
                                                "Invalid fields",
                                                request,
                                                errors));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ProblemDetail> handleValidation(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                List<Map<String, String>> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> {
                                        Map<String, String> err = new HashMap<>();
                                        err.put("field", error.getField());
                                        err.put("message", error.getDefaultMessage());
                                        return err;
                                })
                                .toList();

                log.warn("Validation error: {}", errors);

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(ProblemFactory.create(
                                                HttpStatus.BAD_REQUEST,
                                                ErrorCode.VALIDATION_ERROR,
                                                "Invalid fields",
                                                request,
                                                errors));
        }

        @ExceptionHandler(DomainException.class)
        public ResponseEntity<ProblemDetail> handleDomain(
                        DomainException ex,
                        HttpServletRequest request) {

                HttpStatus status = ex.getCode().getStatus();

                log.warn("Domain error: code={}, message={}", ex.getCode(), ex.getMessage());

                return ResponseEntity
                                .status(status)
                                .body(ProblemFactory.create(
                                                status,
                                                ex.getCode(),
                                                ex.getMessage(),
                                                request,
                                                null));
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ProblemDetail> handleDataIntegrity(
                        DataIntegrityViolationException ex,
                        HttpServletRequest request) {

                log.warn("Database constraint violation", ex);

                ErrorCode code = ErrorCode.BUSINESS_ERROR;
                String message = "Data integrity violation";
                Map<String, Object> details = null;

                Throwable root = ex.getRootCause();

                if (root instanceof org.postgresql.util.PSQLException psqlEx) {

                        String sqlState = psqlEx.getSQLState();

                        switch (sqlState) {

                                case "23505": // UNIQUE
                                        code = ErrorCode.ALREADY_USED;
                                        message = "Resource already exists";
                                        break;

                                case "23502": // NOT NULL
                                        code = ErrorCode.VALIDATION_ERROR;
                                        message = "Required field is missing";

                                        String column = psqlEx.getServerErrorMessage().getColumn();
                                        details = Map.of("field", column);
                                        break;

                                case "23503": // FOREIGN KEY
                                        code = ErrorCode.BUSINESS_ERROR;
                                        message = "Invalid reference";
                                        break;

                                default:
                                        code = ErrorCode.INTERNAL_ERROR;
                                        message = "Unexpected database error";
                        }
                }

                HttpStatus status = code.getStatus();

                return ResponseEntity
                                .status(status)
                                .body(ProblemFactory.create(
                                                status,
                                                code,
                                                message,
                                                request,
                                                details));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ProblemDetail> handleGeneric(
                        Exception ex,
                        HttpServletRequest request) {

                String traceId = UUID.randomUUID().toString();

                log.error("Unexpected error, traceId={}", traceId, ex);

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ProblemFactory.create(
                                                HttpStatus.INTERNAL_SERVER_ERROR,
                                                ErrorCode.INTERNAL_ERROR,
                                                "Unexpected error occurred",
                                                request,
                                                null));
        }
}