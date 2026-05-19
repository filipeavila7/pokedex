package com.example.pokedex.service;

import com.example.pokedex.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

// gerar token jwt e validar
@Service
public class JwtService {
    private static final String SECRET =
            "minha_chave_super_secreta_123456789_minimo_32_chars";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    //  GERAR TOKEN
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail()) // 👈 identifica usuário
                .claim("role", user.getRole().name()) // 👈 ADMIN / USER
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // PEGAR EMAIL DO TOKEN
    public String extractEmail(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    // PEGAR ROLE DO TOKEN
    public String extractRole(String token) {
        return (String) parseClaims(token).getBody().get("role");
    }

    // VALIDAR TOKEN
    public boolean isValid(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return parseClaims(token).getBody().getExpiration().before(new Date());
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

}
