package br.com.demo.services;

import br.com.demo.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    private final long expiresIn = 604800000; // 7 dias
    private static final String key = "ProjVehiclesSales";

    public String generateToken(User user){


        String token = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn))
                .claim("role", user.getRole())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
        System.out.println(token);

        return token;
    }


    public Optional<String> getUserId(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            return Optional.ofNullable(claims.getSubject());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }



}
