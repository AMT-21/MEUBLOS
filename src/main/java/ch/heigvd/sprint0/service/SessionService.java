package ch.heigvd.sprint0.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class SessionService {

    private final String login = "/auth/login";
    private final String register = "/accounts/register";
    @Value("${server.auth.url}")
    private String apiLoginServerUrl = "";
    private final JWTService jwtService;

    private final String tokenName = "tokenJWT";

    @Autowired
    public SessionService(JWTService jwtService){
        this.jwtService = jwtService;
    }

    /**
     * S'occupe de mettre en place le tokenJWT en tant que cookie si la combinaison username/password est acceptée.
     * @param username username de l'utilisateur
     * @param password password de l'utilisateur
     * @param response
     * @return true si le login s'est bien passé et le user est légitime, false sinon
     */
    public boolean setLogin(String username, String password, HttpServletResponse response) {
        String jwtToken = null;
        try {
            jwtToken = doLogin(username, password);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error with login service communication.");
        }

        if(jwtToken != null) {
            Cookie session = new Cookie(tokenName, jwtToken);
            session.setMaxAge(jwtService.getValiditySecondsFromNow(jwtToken));
            response.addCookie(session);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Communique avec le service d'authentification pour récupérer le tokenJWT du login
     * @param username username de l'utilisateur
     * @param password password de l'utilisateur
     * @return Token JWT sous forme de String
     * @throws IOException Si erreur avec la communication avec le service d'authentification
     */
    private String doLogin(String username, String password) throws IOException {

        HttpURLConnection con = preparePostJsonToAuthService(apiLoginServerUrl + login);

        JSONObject toServer = new JSONObject();
        toServer.put("username", username).put("password", password);

        OutputStream os = con.getOutputStream();
        os.write(toServer.toString().getBytes());

        if (con.getResponseCode() != 200) {
            return null;
        }
        InputStream responseStream = con.getInputStream();

        JSONObject response = new JSONObject(new JSONTokener(responseStream));
        return (String) response.get("token");

    }

    /**
     * Permet de vérifier si un user est connecté. Si oui, on récupère son rôle et username.
     * @param request
     * @return Un tableau de string contenant le rôle à l'indice 1 et le username à l'indice 0.
     *         Si il n'y a pas de login ou une erreur d'authenticité, renvoie null
     */
    public String[] checkLogin(HttpServletRequest request) {
        Objects.requireNonNull(request);

        String[] output = null;
        Cookie tokenLoginCookie = findTokenCookie(request);

        if (tokenLoginCookie != null) {     // Le token de login est présent, check de son authenticité
            output = jwtService.extractToken(tokenLoginCookie.getValue());
        }

        return output;
    }


    /**
     * Permet d'enregistrer un nouvel utilisateur
     * @param username username du nouvel utilisateur
     * @param password password du nouvel utilisateur
     * @return Null si tout s'est bien passé, messageur d'erreur sinon
     * @throws IOException Si erreur avec la communication avec le service d'authentification
     */
    public String doRegister(String username, String password) throws IOException {

        HttpURLConnection con = preparePostJsonToAuthService(apiLoginServerUrl + register);

        JSONObject toServer = new JSONObject();
        toServer.put("username", username).put("password", password);

        OutputStream os = con.getOutputStream();
        os.write(toServer.toString().getBytes());
        os.flush();

        int code = con.getResponseCode();

        InputStream responseStream = con.getErrorStream();
        JSONObject res = new JSONObject();
        if (responseStream != null) {
            res = new JSONObject(new JSONTokener(responseStream));
        }

        switch (code){
            case 201: return null;

            case 409: return (String) res.get("error");

            case 422:
                JSONArray arrayErrors = res.getJSONArray("errors");
                StringBuilder errorMessage = new StringBuilder();
                for (int i = 0; i < arrayErrors.length(); i++) {
                    JSONObject error = arrayErrors.getJSONObject(0);
                    errorMessage.append(error.getString("message"));
                }
                return errorMessage.toString();

            default: return "Erreur inconnue";
        }
    }

    /**
     * Permet de supprimer le cookie côté utilisateur lors d'une déconnexion
     * @param response
     */
    public void logout(HttpServletResponse response) {
        // Permet de set l'option SameSite impossible avec l'object Cookie
        // SameSite à 'none' provoque une erreur sur le navigateur
        response.setHeader("Set-Cookie", tokenName +"=; SameSite=strict; Max-Age=0");
    }


    /**
     * Recherche le cookie de session (tokenJWT) parmi les cookies du client
     * @param request
     * @return Le cookie si trouvé, null sinon
     */
    private Cookie findTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenName)) {
                    return cookie;
                }
            }
        }

        return null;
    }

    /**
     * Permet d'initaliser la communication avec le serveur d'authentification.
     * Prépare à une requête POST contenant des data en JSON
     * @param urlToApi l'URL de l'API du serveur d'authentification
     * @return Objet HttpURLConnection préparé pour une requête POST contenant du JSON
     * @throws IOException Si erreur de communication avec le serveur.
     */
    private HttpURLConnection preparePostJsonToAuthService(String urlToApi) throws IOException {
        URL url = new URL(urlToApi);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        return con;
    }

}
