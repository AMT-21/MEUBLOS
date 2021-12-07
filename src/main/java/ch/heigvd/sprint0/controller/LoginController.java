package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Cart;
import ch.heigvd.sprint0.service.ICartService;
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

    private final ICartService cartService;

    private final Utils utils;

    @Autowired
    public LoginController(SessionService sessionService, ICartService cartService, Utils utils) {
        this.sessionService = sessionService;
        this.cartService = cartService;
        this.utils = utils;
    }

    @ModelAttribute("userData")
    public String[] getUser(HttpServletRequest request) {
        return sessionService.checkLogin(request);
    }

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


    @PostMapping("/login")
    public void createUserLoginToken(HttpServletResponse response, HttpSession session, @RequestParam String inputLogin, @RequestParam String inputPassword) throws IOException {

        if (sessionService.setLogin(inputLogin, inputPassword, response)) { // Succes login
            response.sendRedirect("./");
            session.setAttribute("userId", inputLogin);

            Cart cart = cartService.findById(inputLogin).orElse(null);
            if (cart == null) {
                cartService.save(new Cart(inputLogin));
            }
            utils.mergeCarts(session);
        } else { // Error login
            response.sendRedirect("./login?error=true");
        }


    }

}
