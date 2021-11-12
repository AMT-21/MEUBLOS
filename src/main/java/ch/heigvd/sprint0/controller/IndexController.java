package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Pays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

        @GetMapping("/")
        public String findCountries(Model model) {
            List<Pays> countries = new ArrayList<Pays>();

            model.addAttribute("countries", countries);
            return "index";
        }
    }


