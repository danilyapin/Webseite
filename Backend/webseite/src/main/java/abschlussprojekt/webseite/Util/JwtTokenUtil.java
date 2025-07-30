package abschlussprojekt.webseite.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = "my-super-secret-key-which-should-be-very-long-and-secure";
    private static final long EXPIRATION_TIME_MS = 1000 * 60 * 60 * 1; // 1 Stunde

    private static final Key signingKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // JWT-Token erzeugen
// Token erstellen
    public static String generateToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1)) // 1 Stunde
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }



    // Token validieren
    public boolean validateToken(String token) {
        try {
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

    // E-Mail aus Token lesen
    public static String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Rolle aus Token lesen
    public static String extractUserRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Token abgelaufen?
    public static boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // Interne Claims-Methode
    private static Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
