package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @GetMapping("/article/{id}")
    public String findArticle(@PathVariable Long id, Model model){
        Article article= articleService.find(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("article", article);
        return "article";
    }

    @GetMapping("/articles")
    public String findArticles(@RequestParam(required = false) String categorie, Model model) {
        List<Article> articles;
        if(categorie.isEmpty()) { //Si aucun paramètre n'est spécifié
            articles = articleService.findAll(); //Recherche d'article sans paramètre

        } else {
            articles = articleService.findByFilter(categorie); //Recherche via le filtre
        }
        model.addAttribute("articles", articles);
        return "showArticles";
    }
}
