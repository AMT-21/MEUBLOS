package ch.heigvd.sprint0.service;


import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;


@Service
public class JWTService {
    private final String SECRET = "czvFbg2kmvqbcu(7Ux+c";

    public String createToken(String username, String password) {
        JWTCreator.Builder builder = JWT.create().withClaim("userID", 4).withClaim("role", "admin");
        return builder.sign(Algorithm.HMAC256(SECRET));
    }
}
