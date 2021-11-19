package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.repository.ArticleRepository;
import ch.heigvd.sprint0.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("articles", articleRepository.findTop2ByOrderByIdDesc());
        return "index.html";
    }
}


