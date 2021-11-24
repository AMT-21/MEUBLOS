package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Article_Category;
import ch.heigvd.sprint0.model.Article_Category_Ids;
import ch.heigvd.sprint0.model.Category;
import ch.heigvd.sprint0.repository.ArticleCategoryRepository;
import ch.heigvd.sprint0.repository.ArticleRepository;
import ch.heigvd.sprint0.repository.CategoryRepository;
import ch.heigvd.sprint0.service.IArticleService;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class AdminController {

    @Autowired
    private IArticleService articleService;
    @Autowired
    private ArticleCategoryRepository articleCategoriesService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/admin")
    public String admin(Model model) {
        List<Article> articles = articleService.findAll();

        model.addAttribute("articles", articles);
        return "admin.html";
    }

    @GetMapping("/admin/article")
    public String adminArticle(Model model, @RequestParam(name = "id", required = false) String id) {
        if(id != null && !id.chars().allMatch(Character::isDigit))
            return "error";

        Article modelArticle = null;
        if(id != null) {
            Optional<Article> article = articleService.findById(Integer.parseInt(id));
            if(article.isPresent()) {
                modelArticle = article.get();
            }

            for(Article_Category ac : modelArticle.getArticle_category_list()) {
                System.out.println(ac.getCategory().getNameCategory());
            }

        }

        List<Category> categories = (List<Category>) categoryRepository.findAll();
        model.addAttribute("selectedArticle", modelArticle); // article qui se trouve dans l'url
        model.addAttribute("categories", categories);
        model.addAttribute("article", new Article());
        return "adminArticle.html";
    }

    @PostMapping("/admin/article")
    public String articleSubmit(@ModelAttribute Article article, Model model) {
        System.out.println(article.getArticle_category_list());

        /*Article_Category_Ids ids = new Article_Category_Ids(article, new Category("Armoire", new Set<Article_Category>()));
        Set<Article_Category> articleCategories = new HashSet<>();
        articleCategories.add(new Article_Category(ids));
        article.setArticle_category_list(articleCategories);*/

        articleService.addArticle(article);

        return "success";
    }
}
