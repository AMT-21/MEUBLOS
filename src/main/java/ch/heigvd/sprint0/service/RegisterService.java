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
public class RegisterService {

    private final String apiLoginServerUrl = "http://localhost:8081/accounts/register";

    public boolean register(String username, String password, HttpServletResponse response) throws IOException {

        URL url = new URL(apiLoginServerUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        JSONObject toServer = new JSONObject();
        toServer.put("username", username).put("password", password);

        OutputStream os = con.getOutputStream();
        os.write(toServer.toString().getBytes());
        os.flush();

        int test = con.getResponseCode();
        return true;
    }

}
