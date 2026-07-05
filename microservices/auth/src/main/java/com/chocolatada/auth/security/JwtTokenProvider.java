package com.chocolatada.auth.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtTokenProvider {

    private static final String CLAIM_USER_ID = "userId";
    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_TYPE = "type";
    private static final String TOKEN_TYPE_LOGIN = "login";
    private static final String TOKEN_TYPE_VERIFICATION = "verification";
    private static final long TOKEN_EXPIRATION_TIME = 86400000;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(Long userId, String email) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        
        return JWT.create()
                .withClaim(CLAIM_USER_ID, userId)
                .withClaim(CLAIM_EMAIL, email)
                .withClaim(CLAIM_TYPE, TOKEN_TYPE_LOGIN)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String generateVerificationToken(Long userId, String email) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        
        return JWT.create()
                .withClaim(CLAIM_USER_ID, userId)
                .withClaim(CLAIM_EMAIL, email)
                .withClaim(CLAIM_TYPE, TOKEN_TYPE_VERIFICATION)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .sign(algorithm);
    }

    public VerificationTokenData validateVerificationToken(String token) throws JWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);

            String tokenType = decodedJWT.getClaim(CLAIM_TYPE).asString();
            if (!TOKEN_TYPE_VERIFICATION.equals(tokenType))
                throw new JWTVerificationException("El token no es de tipo verificación");

            Long userId = decodedJWT.getClaim(CLAIM_USER_ID).asLong();
            String email = decodedJWT.getClaim(CLAIM_EMAIL).asString();

            return new VerificationTokenData(userId, email);
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token de verificación inválido o expirado", e);
        }
    }
}
