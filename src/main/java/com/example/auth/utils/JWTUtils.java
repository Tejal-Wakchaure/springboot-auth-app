package com.example.auth.utils;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long expirationDate;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .claim("type", "EMAIL_VERIFICATION")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationDate))
                .signWith(getKey())
                .compact();
    }
    public String generatePasswordResetToken(String email) {
        return Jwts.builder()
                .subject(email)
                .claim("type", "PASSWORD_RESET")
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + 900000)
                ) // 15 minutes
                .signWith(getKey())
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
