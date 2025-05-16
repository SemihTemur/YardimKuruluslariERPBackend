package com.semih.service;

import com.semih.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtTokenService {


    private final Key key;

    public JwtTokenService(@Value("${jwt.secret}") String jwtSecret) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    private static final long JWT_EXPIRATION = 2 * 60 * 60 * 1000;

    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();

        User user = (User) authentication.getPrincipal();
        claims.put("user_Id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("displayUsername", user.getDisplayUsername());


        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        claims.put("authorities", authorities);

        // Token oluştur
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail()) // veya başka bir identifier
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(key)
                .compact();
    }

    public String getEmailByToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Map<String, Object> getClaimsFromToken(String token) {
        try {
            // Token'ı çözümleyin

            return Jwts.parserBuilder()
                    .setSigningKey(key) // Secret key kullanarak çözümleme
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            // Token geçerli değilse veya başka bir hata oluşursa
            throw new RuntimeException("JWT token is invalid", e);
        }
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT token süresi dolmuş.", ex.fillInStackTrace());
        } catch (UnsupportedJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT token desteklenmiyor.", ex.fillInStackTrace());
        } catch (MalformedJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("Geçersiz JWT token.", ex.fillInStackTrace());
        } catch (SignatureException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT token imzası doğrulanamadı.", ex.fillInStackTrace());
        } catch (IllegalArgumentException ex) {
            throw new AuthenticationCredentialsNotFoundException("Boş JWT token.", ex.fillInStackTrace());
        }
    }


}
