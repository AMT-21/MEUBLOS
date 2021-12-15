package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.service.SessionService;
import ch.heigvd.sprint0.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@ControllerAdvice
@Controller
public class LoginController {

    private final SessionService sessionService;

    private final Utils utils;

    @Autowired
    public LoginController(SessionService sessionService, Utils utils) {
        this.sessionService = sessionService;
        this.utils = utils;
    }

    /**
     * Model persistant pour remplir le header avec les infos utilisateurs.
     * @param request
     * @return Données utilisateurs, null si pas de session en cours.
     */
    @ModelAttribute("userData")
    public String[] getUser(HttpServletRequest request) {
        return sessionService.checkLogin(request);
    }

    /**
     * Route pour la page de login
     * @param model
     * @param error Présent en cas d'erreur avec le message d'erreur
     * @param request
     * @return La view du login
     */
    @GetMapping("/login")
    public String indexLogin(Model model, @RequestParam(value = "error", required = false) boolean error, HttpServletRequest request) {
        if (error) {    // Login utilisateur faux
            model.addAttribute("error", "Your login failed.");
            return "login";
        }

        if (sessionService.checkLogin(request) != null) {   // Le user est déjà loggué
            return "index";
        }

        return "login";
    }

    /**
     * Route pour l'envoi des données utilisateurs pour le login
     * @param response
     * @param session
     * @param inputLogin
     * @param inputPassword
     * @throws IOException Si erreur de redirection
     */
    @PostMapping("/login")
    public void createUserLoginToken(HttpServletResponse response, HttpSession session, @RequestParam String inputLogin, @RequestParam String inputPassword) throws IOException {

        if (sessionService.setLogin(inputLogin, inputPassword, response)) { // Succes login
            utils.mergeCarts(session, inputLogin);
            response.sendRedirect("./");
        } else { // Error login
            response.sendRedirect("./login?error=true");
        }

    }

    /**
     * Route pour le logout
     * @param response
     * @param session
     * @throws IOException Si erreur de redirection
     */
    @GetMapping("/logout")
    public void logout(HttpServletResponse response, HttpSession session) throws IOException {
        sessionService.logout(response);
        utils.dropCart(session);
        response.sendRedirect("./");
    }


}
