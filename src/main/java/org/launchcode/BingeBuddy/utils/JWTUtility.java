package org.launchcode.BingeBuddy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtility {

    private static final String SECRET_KEY = "MySuperSecretKeyForJWTGeneration1234567890";

    private final Key key;

    public JWTUtility() {
        // Initialize key
        try {
            this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to initialize JWTUtility: Invalid secret key", e);
        }
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiry
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String subject) {
        return (extractSubject(token).equals(subject) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}

