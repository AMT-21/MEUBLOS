package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Cart_Article;
import ch.heigvd.sprint0.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;

@Controller
public class IndexController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        model.addAttribute("articles", articleRepository.findTop3ByOrderByIdDesc());
        session.setAttribute("articles_in_cart", new LinkedList<>());
        return "index.html";
    }
}


