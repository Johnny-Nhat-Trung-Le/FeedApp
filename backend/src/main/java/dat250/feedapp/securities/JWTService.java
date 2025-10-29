package dat250.feedapp.securities;

import io.jsonwebtoken.Jwts;
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
    //Metric is in Hour
    private final Integer EXPIREYTIME = 1;
    private final String KEYALGORITHM = "HMACSHA256";
    private final Key SECRETKEY;

    public JWTService() {
        this.SECRETKEY = generateKey();
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofHours(EXPIREYTIME));
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
}
