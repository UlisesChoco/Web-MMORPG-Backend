package com.chocolatada.auth.api.advice;

import com.chocolatada.auth.api.response.VerificationResponse;
import com.chocolatada.auth.exception.EmailFailedVerificationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmailVerificationControllerAdvice {
    @ExceptionHandler(EmailFailedVerificationException.class)
    public ResponseEntity<VerificationResponse> handleEmailVerificationException(
            EmailFailedVerificationException e
    ) {
        VerificationResponse response = new VerificationResponse(
            false,
            e.getMessage()
        );

        return ResponseEntity
                .internalServerError()
                .body(response);
    }
}
