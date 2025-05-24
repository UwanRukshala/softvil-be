package lk.softvil.assignment.eventm.security.jwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lk.softvil.assignment.eventm.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationInMs;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateToken(User user) {

        String username = user.getName();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .subject(username)  // Changed from setSubject()
                .claim("role", user.getRole())
                .issuedAt(now)      // Changed from setIssuedAt()
                .expiration(expiryDate) // Changed from setExpiration()
                .signWith(key()) // Changed from signWith(key())
                .compact();
    }

    public String getUsernameFromJWT(String token) {
//        Claims claims = Jwts.parser()
//                .verifyWith(key())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//
//        return claims.getSubject();

        return null;
    }

    public boolean validateToken(String authToken) {
        try {
//            Jwts.parser()
//                    .verifyWith(key())
//                    .build()
//                    .parseSignedClaims(authToken);
            return true;
        } catch (ExpiredJwtException ex) {
            // log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            // log.error("Unsupported JWT token");
        } catch (MalformedJwtException ex) {
            // log.error("Invalid JWT token");
        } catch (SignatureException ex) {
            // log.error("Invalid JWT signature");
        } catch (IllegalArgumentException ex) {
            // log.error("JWT claims string is empty");
        }
        return false;
    }
}