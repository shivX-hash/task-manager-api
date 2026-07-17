package com.shiv.task_manager_api.service;

import com.shiv.task_manager_api.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.function.Function;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    
    private static final String SECRET =  "mysecretkeymysecretkeymysecretkey123456"; 

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes()); 
    }

    public String generateToken(User user){
        return Jwts.builder()
              .setSubject(user.getUsername())
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis() +1000*60*60*24)
            )
            .signWith(getSigningKey(),SignatureAlgorithm.HS256)
            .compact();
    } 

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
             .setSigningKey(getSigningKey())
             .build()
             .parseClaimsJws(token)
             .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token)
                  .before(new Date());
    }
    
    public boolean isTokenValid(String token,UserDetails userDetails){
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
         && !isTokenExpired(token);
    }

}
