
package com.healthcare.appointment.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey signingKey;

    public JwtUtil(
            @Value("${security.jwt.secret}") String secret
    ) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException(
                    "JWT secret must not be empty"
            );
        }

        // Decode secret exactly like Identity Service
        byte[] keyBytes = Decoders.BASE64URL.decode(secret);

        if (keyBytes.length < 32) {
            throw new IllegalArgumentException(
                    "JWT secret must contain at least 32 decoded bytes"
            );
        }

        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {

        Date expiration = extractExpiration(token);

        return expiration.before(new Date());
    }

    public boolean validateToken(String token) {

        try {
            Claims claims = extractAllClaims(token);

            return claims.getSubject() != null
                    && claims.getExpiration() != null
                    && claims.getExpiration().after(new Date());

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public boolean isTokenValid(
            String token,
            String expectedUsername
    ) {

        try {
            return expectedUsername.equals(extractUsername(token))
                    && validateToken(token);

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }
}
