package com.example.usergym.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.util.Date;

@Component
public class JwtUtil {

    private final KeyPair keys;
    private final long expirationMs;

    public JwtUtil(KeyPair keys,
                   @Value("${jwt.expiration-ms}") long expirationMs) {
        this.keys = keys;
        this.expirationMs = expirationMs;
    }

    public String generateToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationMs))
                .signWith(keys.getPrivate(), SignatureAlgorithm.RS256)
                .compact();
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keys.getPublic())
                .build()
                .parseClaimsJws(token);
    }
}
