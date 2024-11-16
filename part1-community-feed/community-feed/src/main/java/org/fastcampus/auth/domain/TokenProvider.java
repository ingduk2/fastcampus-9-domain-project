package org.fastcampus.auth.domain;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
public class TokenProvider {

    private final SecretKey key;
    private static final long TOKEN_INVALID_TIME = 1000L * 60 * 60; // 1 hour

    public TokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(UTF_8));
    }

    public String createToken(Long userId, String role) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + TOKEN_INVALID_TIME);
        try {
            return Jwts.builder()
                    .subject(userId.toString())
                    .issuedAt(now)
                    .expiration(expirationDate)
                    .claim("role", role)
                    .signWith(key)
                    .compact();
        } catch (JwtException e) {
            throw new IllegalStateException("Failed to create JWT token", e);
        }
    }

    public Long getUserId(String token) {
        try {
            Claims claims = parseToken(token);
            return Long.parseLong(claims.getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalStateException("Invalid JWT token: failed to extract user ID", e);
        }
    }

    public String getUserRole(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.get("role", String.class);
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalStateException("Invalid JWT token: failed to extract user role", e);
        }
    }

    private Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.error("JWT token has expired");
            throw new IllegalStateException("JWT token has expired", e);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported");
            throw new IllegalStateException("JWT token is unsupported", e);
        } catch (MalformedJwtException e) {
            log.error("JWT token is malformed");
            throw new IllegalStateException("JWT token is malformed", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT token is null or empty");
            throw new IllegalStateException("JWT token is null or empty", e);
        }
    }
}
