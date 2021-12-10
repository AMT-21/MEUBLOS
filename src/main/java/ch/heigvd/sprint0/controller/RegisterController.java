
package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RegisterController {

    private final RegisterService registerService;
	
    private SessionService sessionService;

    @Autowired
    public RegisterController(RegisterService registerService, SessionService sessionService) {
        this.registerService = registerService;
		this.sessionService = sessionService;
    }

    private String errorMessage;

    @GetMapping("/register")
    public String index(Model model, @RequestParam(value = "error", required = false) boolean error) {
        if(error){
            model.addAttribute("error", errorMessage);
        }
        return "register";
    }

    @PostMapping("/register")
    public void createUser(HttpServletResponse response, @RequestParam String inputUsername, @RequestParam String inputPassword) throws IOException {

         errorMessage = sessionService.doRegister(inputUsername, inputPassword, response);
        if (errorMessage == null) {
            response.sendRedirect("./login");
        } else {
            response.sendRedirect("./register?error=true");
        }
    }
}
