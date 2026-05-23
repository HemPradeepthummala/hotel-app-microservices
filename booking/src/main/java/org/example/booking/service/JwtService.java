package org.example.booking.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;


@Service
public class JwtService {
    private final String SECRET= "SecretkeyisSecureSecretkeyisSecureSecretkeyisSecure";

    public String generateToken(String username){
        return Jwts.builder().subject(username).issuedAt(new Date()).expiration(
                new Date(System.currentTimeMillis() + 3600000))
                        .signWith(getKey()).
                compact();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith((SecretKey)getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validate(String token) {

        try {
            Jwts.parser()
                    .verifyWith((SecretKey)getKey())
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
