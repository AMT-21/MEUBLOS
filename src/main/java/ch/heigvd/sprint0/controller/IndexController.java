package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.service.ArticleService;
import ch.heigvd.sprint0.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    private final Utils utils;

    private final ArticleService articleService;

    @Autowired
    public IndexController(Utils utils, ArticleService articleService) {
        this.utils = utils;
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session
            , HttpServletRequest req, HttpServletResponse resp) {
        model.addAttribute("articles", utils.getArticlesInfo(articleService.find3LatestArticles(), session));
        return "index";
    }
}


