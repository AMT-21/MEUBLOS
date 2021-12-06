package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.service.JWTService;
import ch.heigvd.sprint0.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@ControllerAdvice
@Controller
public class LoginController {

    @Autowired
    private SessionService sessionService;

    @ModelAttribute("userData")
    public String[] getUser(HttpServletRequest request) {
        return sessionService.checkLogin(request);
    }

    @GetMapping("/login")
    public String indexLogin(Model model, @RequestParam(value = "error", required = false) boolean error, HttpServletRequest request) {
        if (error) {    // Login utilisateur faux
            model.addAttribute("error", "Your login failed.");
            return "login.html";
        }

        if (sessionService.checkLogin(request) != null) {   // Le user est déjà loggué
            return "index.html";
        } else {
            return "login.html";
        }


    }


    @PostMapping("/login")
    public void createUserLoginToken(HttpServletResponse response, @RequestParam String inputLogin, @RequestParam String inputPassword) throws IOException {

        if (sessionService.setLogin(inputLogin, inputPassword, response)) { // Succes login
            response.sendRedirect("./");
        } else { // Error login
            response.sendRedirect("./login?error=true");
        }


    }

}
