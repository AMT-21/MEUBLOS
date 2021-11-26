package ch.heigvd.sprint0.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionService {

    @Autowired
    private JWTService jwtService;

    public boolean setLogin(String username, String password, HttpServletResponse response) {
        if (username.equals("admin") && password.equals("admin")) { // Pour l'instant, on accepte que cette paire user/mdp
            String token = jwtService.createToken(username, password);
            Cookie session = new Cookie("token", token);
            response.addCookie(session);
            return true;
        } else {
            return false;
        }
    }

//    private String setCookie(String token) {
//
//    }
}
