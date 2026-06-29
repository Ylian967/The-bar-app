package fr.barapp.barapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/** Génère et vérifie les jetons JWT. */
@Service
public class JwtService {

    private final SecretKey cle;
    private final long dureeMs;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration-ms}") long dureeMs) {
        this.cle = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.dureeMs = dureeMs;
    }

    public String genererToken(String email, String role) {
        Date maintenant = new Date();
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(maintenant)
                .expiration(new Date(maintenant.getTime() + dureeMs))
                .signWith(cle)
                .compact();
    }

    public String extraireEmail(String token) {
        return parser(token).getPayload().getSubject();
    }

    public boolean estValide(String token) {
        try {
            parser(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parser(String token) {
        return Jwts.parser().verifyWith(cle).build().parseSignedClaims(token);
    }
}
