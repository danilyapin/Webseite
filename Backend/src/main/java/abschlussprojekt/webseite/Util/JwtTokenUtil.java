package abschlussprojekt.webseite.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final long EXPIRATION_TIME_MS = 1000 * 60 * 60;

    private static final Key signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String email, String role) {

        String roleWithoutPrefix = role.replace("ROLE_", "");

        return Jwts.builder()
                .setSubject(email)
                .claim("role", roleWithoutPrefix)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            if (isTokenExpired(token)) {
                System.out.println("Token ist abgelaufen");
                return false;
            }
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Ungültiger oder abgelaufener JWT-Token: " + e.getMessage());
            return false;
        }
    }

    public static String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public static String extractUserRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public static boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private static Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
