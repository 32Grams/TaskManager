package ru.semenov.itpower.taskmanager.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {

        private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        private final long validityInMilliseconds = 360000L;

        public String generateToken(UserDetails userDetails) {
            Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
            Date now = new Date();
            Date validity = new Date(now.getTime() + validityInMilliseconds);
            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(validity)
                    .signWith(secretKey)
                    .compact();
        }


        public Claims getClaims(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

        public String getUsername(String token) {
            return getClaims(token).getSubject();
        }

        public boolean isTokenExpired(String token) {
            return getClaims(token).getExpiration().before(new Date());
        }

        public Authentication getAuthentication(String token, UserDetails userDetails) {
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }
}
