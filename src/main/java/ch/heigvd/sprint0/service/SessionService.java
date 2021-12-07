package ch.heigvd.sprint0.service;


import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class SessionService {

    private final String login = "/auth/login";
    private final String register = "/accounts/register";
    private FileReader apiLoginServerUrlJson;
    private JSONObject loginConfig;
    private String apiLoginServerUrl;


    @Autowired
    private JWTService jwtService;

    public SessionService(){
        try {
            apiLoginServerUrlJson = new FileReader("./config.json", StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            System.out.println("Error, config(s) file(s) missing or cannot been read. Please check README.");
            System.exit(-1);
        }
        loginConfig = new JSONObject(new JSONTokener(new BufferedReader (apiLoginServerUrlJson)));
        apiLoginServerUrl = (String) loginConfig.get("apiAuthenticationServer");
    }

    public boolean setLogin(String username, String password, HttpServletResponse response) {
        String jwtToken = null;
        try {
            jwtToken = doLogin(username, password);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error with login service communication.");
        }

        if(jwtToken != null) {
            Cookie session = new Cookie("token", jwtToken);
            response.addCookie(session);
            return true;
        } else {
            return false;
        }

    }

    private String doLogin(String username, String password) throws IOException {

        URL url = new URL(apiLoginServerUrl + login);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

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

    public String doRegister(String username, String password, HttpServletResponse response) throws IOException {

        URL url = new URL(apiLoginServerUrl + register);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        JSONObject toServer = new JSONObject();
        toServer.put("username", username).put("password", password);

        OutputStream os = con.getOutputStream();
        os.write(toServer.toString().getBytes());
        os.flush();

        int code = con.getResponseCode();

        InputStream responseStream = con.getErrorStream();

        JSONObject res = new JSONObject(new JSONTokener(responseStream));

        switch (code){
            case 201: return null;

            case 409: return (String) res.get("error");

            case 422:

            default: return "Erreur inconnue";
        }
    }

}
