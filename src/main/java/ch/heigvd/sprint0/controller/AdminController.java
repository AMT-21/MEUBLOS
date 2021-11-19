package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private IArticleService articleService;

    @GetMapping("/admin")
    public String admin(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "admin.html";
    }
}
