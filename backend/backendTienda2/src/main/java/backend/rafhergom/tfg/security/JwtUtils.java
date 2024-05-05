package backend.rafhergom.tfg.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;

public class JwtUtils {

    private static final String SECRET_KEY = "your_secret_key"; // Change this to a secure key

    private static SecretKey getSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    @SuppressWarnings("deprecation")
	public String generateToken(String username, String role) {
    	long expirationTime = 30 * 60 * 1000; // 30 minutos en milisegundos
        long now = System.currentTimeMillis();
        long expirationDate = now + expirationTime;

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expirationDate))
                .signWith(getSecretKey())
                .compact();
    }

    public String parseToken(String token) {
        try {
            JwtParser jwtParser = (JwtParser) Jwts.parser().setSigningKey(getSecretKey());
            Jws<Claims> jwt = jwtParser.parseClaimsJws(token);

            System.out.println(jwt.getBody().getSubject());
            return "Valid";

        } catch (io.jsonwebtoken.security.SignatureException jwtException) {
            jwtException.printStackTrace();
            return null;
        }
    }
}

