package ch.heigvd.sprint0.service;


import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;

import java.util.Date;
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

    /**
     * Décode le token pour extraire le rôle et le username. Si token pas valide, retourne Null
     * @param token token JWT en String
     * @return Tableau de String, indice 0 = username, indice 1 = rôle
     */
    public String[] extractToken(String token) {
        Objects.requireNonNull(token);

        // Vérification de l'authenticité du token
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            verifier.verify(token);
        } catch (JWTVerificationException exception) {
            return null;
        }

        String[] output = new String[2];
        // Extraction des infos nécessaires dans le token
        DecodedJWT decodedJWT = JWT.decode(token);
        output[0] = decodedJWT.getClaim("sub").asString();
        output[1] = decodedJWT.getClaim("role").asString();

        return output;
    }

    public int getValiditySecondsFromNow(String jwt) {
        DecodedJWT decodedJWT = JWT.decode(jwt);
        Date expires = decodedJWT.getExpiresAt();
        Date now = new Date();
        long secondsToExpire = expires.getTime() / 1000L - now.getTime() / 1000L;
        return (int) secondsToExpire;
    }
}
