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

@Service
public class SessionService {

    private final String apiLoginServerUrl = "http://localhost:9081/auth/login";

    @Autowired
    private JWTService jwtService;

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

        URL url = new URL(apiLoginServerUrl);
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

}
