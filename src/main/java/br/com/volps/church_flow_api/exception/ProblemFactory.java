package br.com.volps.church_flow_api.exception;

import java.time.Instant;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import jakarta.servlet.http.HttpServletRequest;

public class ProblemFactory {

    public static ProblemDetail create(
            HttpStatus status,
            ErrorCode code,
            String message,
            HttpServletRequest request,
            Object errors) {

        ProblemDetail problem = ProblemDetail.forStatus(status);

        // RFC 7807 fields
        problem.setTitle(code.getDescription());
        problem.setDetail(message);
        problem.setProperty("type", code.name());

        // extensions (custom fields)        
        problem.setProperty("timestamp", Instant.now().toEpochMilli());

        String traceId = MDC.get("traceId");
        if (traceId != null) {
            problem.setProperty("traceId", traceId);
        }

        if (errors != null) {
            problem.setProperty("errors", errors);
        }

        return problem;
    }
}
