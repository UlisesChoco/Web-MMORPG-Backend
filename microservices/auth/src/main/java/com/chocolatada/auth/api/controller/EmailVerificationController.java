package com.chocolatada.auth.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.chocolatada.auth.api.response.VerificationResponse;
import com.chocolatada.auth.service.jpa.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class EmailVerificationController {
    private final IUserService userService;

    @GetMapping("/verify")
    public ResponseEntity<VerificationResponse> verifyEmail(@RequestParam String token) {
        try {
            userService.markEmailAsVerified(token);

            VerificationResponse response = new VerificationResponse(
                true,
                "Correo electrónico verificado exitosamente. Tu cuenta está activada."
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            VerificationResponse response = new VerificationResponse(
                false,
                e.getMessage()
            );
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
