package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.ArticleCategory;
import ch.heigvd.sprint0.model.Category;
import ch.heigvd.sprint0.service.IArticleCategoryService;
import ch.heigvd.sprint0.service.IArticleService;
import ch.heigvd.sprint0.service.ICategoryService;
import ch.heigvd.sprint0.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ShopController {
    private final Utils utils;

    private final IArticleService articleService;

    private final IArticleCategoryService articleCategoryService;

    private final ICategoryService categoryService;

    @Autowired
    public ShopController(Utils utils, IArticleService articleService, IArticleCategoryService articleCategoryService, ICategoryService categoryService) {
        this.utils = utils;
        this.articleService = articleService;
        this.articleCategoryService = articleCategoryService;
        this.categoryService = categoryService;
    }

    @GetMapping("/shop/{id}")
    public String findArticle(@PathVariable int id, Model model, HttpSession session) {
        Optional<Article> article = articleService.findById(id);
        List<Category> categories = new ArrayList<>();
        if(article.isPresent())
        {
            // Plutôt ajouter les catégories dans ArticleInfo
            List<ArticleCategory> article_category = articleCategoryService.findAllByArticle(article.get().getId());
            for(ArticleCategory a_c : article_category)
            {
                categories.add(a_c.getCategory());
            }
            model.addAttribute("categories", categories);
            model.addAttribute("article", utils.getArticleInfo(article.get(),session));
        }
        return "article_detail";
    }

    @GetMapping("/shop")
    public String findArticles(@RequestParam(required = false) String namecategory, Model model, HttpSession session) {
        List<Article> articles = new ArrayList<>();

        if(namecategory == null) {
            articles = articleService.findAll();
        } else {
            //Recherche d'un nom de catégorie correspondant dans la BDD
            Category category = categoryService.findByName(namecategory).orElse(null);
            if(category != null) {
                //Recherche des entrées de cette catégorie contenant également un article
                articles = articleService.findAllWithCategory(namecategory);
            }
        }

        List<Category> lsCategories = categoryService.findAllIfUsed();

        model.addAttribute("categories", lsCategories);
        model.addAttribute("articles", utils.getArticlesInfo(articles,session));

        return "shop";
    }
}
