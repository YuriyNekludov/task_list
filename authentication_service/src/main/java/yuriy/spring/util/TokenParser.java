package yuriy.spring.util;

import io.jsonwebtoken.Jwts;
import lombok.experimental.UtilityClass;

import javax.crypto.SecretKey;
import java.security.Key;

@UtilityClass
public class TokenParser {

    public String getUserIdFromToken(String token, Key key) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id")
                .toString();
    }

    public String getUsernameFromToken(String token, Key key) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
