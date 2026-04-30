package com.chocolatada.auth.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerificationTokenData {
    private final Long userId;
    private final String email;
}