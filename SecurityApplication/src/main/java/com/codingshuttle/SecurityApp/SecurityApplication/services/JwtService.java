package com.codingshuttle.SecurityApp.SecurityApplication.services;

import com.codingshuttle.SecurityApp.SecurityApplication.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
@Service
public class JwtService
{
    @Value("${jwt.secretKey}")
    private String jwtToken;

    private SecretKey getSecretKey()
    {
        return Keys.hmacShaKeyFor(jwtToken.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user)
    {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email" , user.getEmail())
                .claim("roles", Set.of("ADMIN","USER"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600*1000))
                .signWith(getSecretKey())
                .compact();
    }


    public Long getUserIdFromToken(String token)
    {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }
//    1. Jwts.parser() → Creates JWT parser builder.
//
//            2. .verifyWith(getSecretKey()) → Sets secret key for signature verification.
//
//            3. .build() → Creates final parser object.
//
//            4. .parseSignedClaims(token) → Verifies and parses signed JWT token.
//
//            5. .getPayload() → Extracts claims(payload) from token.
//
//            6. claims.getSubject() → Reads stored userId from token.
//
//7. Long.valueOf(...) → Converts String userId into Long type.

}
