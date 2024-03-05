package com.example.cafe.service;



import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.cafe.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final String SECRET_KEY="ed17c1df3ba0f48b7dd58deae865e46d8e925ef845dca13061066e7fa6a0f9f8";
    

    public String extractUsername(String token){
        return extractAllClaim(token,Claims::getSubject);
    }

    
    public boolean isTokenValid(String token,UserDetails user){
        String username=extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
          }


private Date extractExpiration(String token) {
    return extractAllClaim(token,Claims::getExpiration);
   }


public <T> T extractAllClaim(String token , Function<Claims,T> claimsresolver){
    Claims claims=extractAllClaims(token);
    return claimsresolver.apply(claims);
}

private Claims extractAllClaims(String token){
    return Jwts
    .parser()
    .verifyWith(getSigninKey())
    .build()
    .parseSignedClaims(token)
    .getPayload();
}

public String generateToken(User user){
    String token=Jwts
    .builder()
    .subject(user.getUsername())
    .issuedAt(new Date(System.currentTimeMillis()))
    .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
    .signWith(getSigninKey()) 
    .compact();
    return token;
    
    
    
}

private SecretKey getSigninKey() {
    byte[] keyBytes=Decoders.BASE64URL.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
   }


}


