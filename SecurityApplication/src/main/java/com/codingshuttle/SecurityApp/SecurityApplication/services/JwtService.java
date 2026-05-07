package com.codingshuttle.SecurityApp.SecurityApplication.services;

import com.codingshuttle.SecurityApp.SecurityApplication.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

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
}

//**1. `@Service`** → Spring Boot creates object(bean) of `JwtService` class automatically.
//
//**2. `@Value("${jwt.secretKey}")`** → Reads secret key value from `application.properties`.
//
//**3. `jwtToken` variable** → Stores secret key string from properties file.
//
//**4. `generateToken(User user)` method called** → Starts JWT token creation process.
//
//**5. `Jwts.builder()`** → Creates JWT builder object for building token.
//
//**6. `.setSubject(user.getId().toString())`** → Stores user id inside token as subject.
//
//**7. `.claim("email", user.getEmail())`** → Adds custom email data inside token.
//
//**8. `.claim("roles", Set.of("ADMIN","USER"))`** → Adds user roles inside token payload.
//
//**9. `.setIssuedAt(new Date())`** → Stores current login time in token.
//
//**10. `.setExpiration(...)`** → Sets token expiry time to 1 hour.
//
//**11. `getSecretKey()` method called** → Generates secret signing key for JWT.
//
//**12. `jwtToken.getBytes(StandardCharsets.UTF_8)`** → Converts secret string into byte array.
//
//**13. `Keys.hmacShaKeyFor(...)`** → Creates `SecretKey` object using HMAC algorithm.
//
//**14. `.signWith(getSecretKey())`** → Digitally signs token using secret key.
//
//**15. `.compact()`** → Converts JWT object into final encoded JWT string.
//
//**16. Final JWT token returned** → Token is sent back to client/user.git