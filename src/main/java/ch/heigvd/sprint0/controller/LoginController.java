package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.service.JWTService;
import ch.heigvd.sprint0.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class LoginController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/login")
    public String indexLogin(HttpServletRequest request, Model model, @RequestParam(value = "error", required = false) boolean error) {
        if (error) {    // Login utilisateur faux
            model.addAttribute("error", "Your login failed.");
        }

        return "login.html";

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
