package ch.heigvd.sprint0.service;


import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;

import java.util.Objects;


@Service
public class JWTService {
    // TODO DPO - Il y a du code en dur qui se balade dans votre projet. Je vous invite à mettre tout ça
    //  soit dans le fichier properties ou alors dans un fichier contenant des constantes. À vous de voir où va quoi

    // TODO Stef - Mettre le secret dans un fichier config visible par le public sur le repo ?
    private final String SECRET = "czvFbg2kmvqbcu(7Ux+c";

    public String createToken(String username, String password) {
        JWTCreator.Builder builder = JWT.create().withClaim("userID", 4).withClaim("role", "admin");
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    public boolean checkTokenAuthenticity(String token) {
        Objects.requireNonNull(token);

        //DecodedJWT decodedJWT = JWT.decode(token);
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            verifier.verify(token);
        } catch (JWTVerificationException exception) {
            return false;
        }

        return true;
    }
}
