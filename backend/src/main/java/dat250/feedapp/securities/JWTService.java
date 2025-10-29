package dat250.feedapp.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    //Metric is in minutes
    private final Integer EXPIREYTIME = 15;
    private final String KEYALGORITHM = "HMACSHA256";
    private final Key SECRETKEY;

    public JWTService() {
        this.SECRETKEY = generateKey();
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMinutes(EXPIREYTIME));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(SECRETKEY)
                .compact();

    }

    /**
     * Generate the {@link Key} used to sign the JWT
     * Currently uses the algorithm in KEYALGORITHM
     *
     * @return {@link Key}
     */
    private Key generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(KEYALGORITHM);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the claims given the token
     *
     * @param token
     * @return
     */
    private Claims getClaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRETKEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String getUsernameByToken(String token) {
        Claims claims = getClaim(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    /**
     * Check if the token username is the same as the username from {@link UserDetails}
     * and also check whether the token has been expired
     *
     * @param token       token value
     * @param userDetails details of the user
     * @return true if the token connected to the {@link UserDetails} is valid, false otherwise
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String usernameFromToken = getUsernameByToken(token);
        Claims claims = getClaim(token);
        return (claims.getExpiration().after(Date.from(Instant.now())))
                &&
                (usernameFromToken.equals(userDetails.getUsername()));

    }


}
