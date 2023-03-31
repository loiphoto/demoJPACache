package com.example.demojpacache.security;

import com.example.demojpacache.Entity.Role;
import com.example.demojpacache.Entity.User;
import com.example.demojpacache.repository.RoleRepository;
import com.example.demojpacache.service.impl.CustomUserDetailsService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil {

    private final String SECRET = "bezKoderSecretKey";

    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenUtil(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    public UserDetails parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                     .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(body.getSubject());
            return userDetails;

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param user the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(UserSercutityImpl user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(new Date().getTime() + 60000*30));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }


    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
