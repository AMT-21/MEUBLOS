package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.object.ArticleInfo;
import ch.heigvd.sprint0.service.IArticleService;
import ch.heigvd.sprint0.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ShopController {
    @Autowired
    private Utils utils;

    @Autowired
    private IArticleService articleService;

    @GetMapping("/shop")
    public String findArticles(Model model, HttpSession session) {
        model.addAttribute("articles", utils.getArticlesInfo(articleService.findAll(), session));
        return "shop.html";
    }
}
