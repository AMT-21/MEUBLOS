package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @GetMapping("/articles")
    public String findArticles(Model model) {
        List<Article> articles = (List<Article>) articleService.findAll();
        model.addAttribute("articles", articles);
        return "showArticles";
    }
}
