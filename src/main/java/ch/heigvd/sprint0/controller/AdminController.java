package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Article_Category;
import ch.heigvd.sprint0.model.Category;
import ch.heigvd.sprint0.repository.ArticleCategoryRepository;
import ch.heigvd.sprint0.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ArticleCategoryRepository articleCategoriesService;

    @GetMapping("/admin")
    public String admin(Model model) {
        List<Article> articles = articleService.findAll();
        List<String> articlesCategoriesStr = new ArrayList<>();

        for(Article a : articles) {
            String str = "";
            List<Article_Category> cats = articleCategoriesService.findArticle_CategoriesByIds_Article(a);
            int i = 0;
            for(Article_Category ac : cats) {
                str += ac.getCategory().getNameCategory();
                if(i + 1 != cats.size()) {
                    str += ", ";
                }
                i++;
            }
            articlesCategoriesStr.add(str);
        }

        for(String str : articlesCategoriesStr) {
            System.out.println(str);
        }
        model.addAttribute("articles", articles);
        model.addAttribute("atriclesCategories", articlesCategoriesStr);
        return "admin.html";
    }
}
