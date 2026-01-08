package help.buddy.ai.backend.services;

import help.buddy.ai.backend.entity.User;
import help.buddy.ai.backend.utility.Apistatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTService {
    private static final String jwtSecreteKey = "xUKFxik/HNawupRRqQtwGCDj33DM/dZXoP6hpj/x2Ik=";

    //generate a secreate key method
    private SecretKey generateSecreteKey(){
        return Keys.hmacShaKeyFor(jwtSecreteKey.getBytes(StandardCharsets.UTF_8));
    }
    public String createToken(User user) {
        return Jwts.builder()         //to build
                .subject(String.valueOf(user.getId()))
                .claim("email",user.getEmail())
                .claim("name", user.getName())
                .claim("role", Apistatus.FAILED)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60))
                .signWith(generateSecreteKey())
                .compact();
    }

    public Long generateUserIdFromToken(String token) {
        Claims claims = Jwts.parser()   // to parse it
                .verifyWith(generateSecreteKey())// verify the key that we have
                .build()   // build the parser
                .parseSignedClaims(token)   //parse the token and get jws<claims> 
                .getPayload();  //to get the payload

        return Long.valueOf(claims.getSubject());  //Assuming the user ID is stored as the subject in the token
    }
}
