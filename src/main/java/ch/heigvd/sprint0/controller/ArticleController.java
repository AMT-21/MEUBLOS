package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.service.IArticleService;
import ch.heigvd.sprint0.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    Utils utils;

    @GetMapping("/articles/{id}")
    public String findArticle(@PathVariable int id, Model model, HttpSession session) {
        Optional<Article> article = articleService.findById(id);
        if(article.isPresent())
        {
            model.addAttribute("article", utils.getArticleInfo(article.get(),session));
        }
        return "article_detail.html";
    }

    @GetMapping("/articles")
    public String findArticles(@RequestParam(required = false) String categorie, Model model) {
        List<Article> articles;
        articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "shop.html";
    }
}
