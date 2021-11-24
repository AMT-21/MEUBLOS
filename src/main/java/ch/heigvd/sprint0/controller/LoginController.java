package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

    @Autowired
    private JWTService jwtService;

    @GetMapping("/login")
    public String indexLogin() {
        return "login.html";
    }


    @PostMapping("/login")
    public ResponseEntity<String> createUserLoginToken(@RequestParam String inputLogin, @RequestParam String inputPassword) {
        String token;

        if (inputLogin.equals("admin") && inputPassword.equals("admin")) { // Pour l'instant, on accepte que cette paire user/mdp
            token = jwtService.createToken(inputLogin, inputPassword);
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.ok("Pas good");
    }

}
