package com.Project_Management.ServicesImpl;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class Jwtgenerator {

    private String SecretKey = "";

    public Jwtgenerator() {
        try {

            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            javax.crypto.SecretKey sk = keyGen.generateKey();
            SecretKey = Base64.getEncoder().encodeToString(sk.getEncoded());

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    public String JWTgenerator(String username) throws Exception {

        Map<String, Object> claim = new HashMap<>();

        return Jwts
                .builder()
                .claims()
                .add(claim)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1200000))
                .and()
                .signWith(getkey())
                .compact();
    }
    private javax.crypto.SecretKey getkey() {
        byte[] keybyte = Decoders.BASE64.decode(SecretKey);
        return Keys.hmacShaKeyFor(keybyte);
    }
    



    // These are use to Valid The Jwt.

    public String extractusername(String token) {
        return extractclaims(token,Claims::getSubject);
    }

    private <T>T extractclaims(String token, Function<Claims,T> claimsResolver) {
        
        final Claims claims = extractAllToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllToken(String token) {
        if (token == null || token.trim().isEmpty()) {
        throw new IllegalArgumentException("JWT token is null or empty.");
    }

    return Jwts
            .parser()
            .verifyWith(getkey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public boolean validateToken(String token, UserDetails user) {
        final String userName = extractusername(token);
        return (userName.equals(user.getUsername()) && !isTokenExpired(token));
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    private Date extractExpiration(String token) {
        
        return extractclaims(token, Claims::getExpiration);
    }
}
