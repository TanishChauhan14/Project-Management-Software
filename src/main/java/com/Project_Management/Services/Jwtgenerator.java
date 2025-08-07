package com.Project_Management.Services;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;

import org.springframework.stereotype.Service;

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
                .expiration(new Date(System.currentTimeMillis() + 60 * 10 * 10))
                .and()
                .signWith(getkey())
                .compact();
    }

    private Key getkey() {
        byte[] keybyte = Decoders.BASE64.decode(SecretKey);
        return Keys.hmacShaKeyFor(keybyte);
    }
}
