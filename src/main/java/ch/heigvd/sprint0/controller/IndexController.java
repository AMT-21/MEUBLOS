package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.repository.ArticleRepository;
import ch.heigvd.sprint0.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private Utils utils;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        model.addAttribute("articles", utils.getArticlesInfo(articleRepository.findTop3ByOrderByIdDesc(), session));
        return "index.html";
    }
}


