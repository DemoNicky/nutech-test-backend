package com.nutech.test_project.Service.Impl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${spring.jwt.secret-key}")
    private String SECRET_KEY;

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 12;

    public String generateAccessToken(Authentication authentication){
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .subject(userPrincipal.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isValidToken(String token, UserDetails userDetails){
        final String username = extractUsernameFromToken(token);

        if (!username.equals(userDetails.getUsername())) {
            return false;
        }
        try {
            Jwts.parser().verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch (SignatureException e){
            log.error("Invalid Jwt Signature: {}", e.getMessage());
        }catch (MalformedJwtException e){
            log.error("Invalid Jwt Token: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            log.error("Jwt Token is Expired: {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("Jwt Token is Unsupported: {}", e.getMessage());
        }catch (IllegalArgumentException e){
            log.error("Jwt claims string empty: {}", e.getMessage());
        }
        return false;
    }
    
    public String extractUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
