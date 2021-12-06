package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.ArticleCategory;
import ch.heigvd.sprint0.model.Category;
import ch.heigvd.sprint0.repository.CategoryRepository;
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
    @Autowired
    private Utils utils;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IArticleCategoryService articleCategoryService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/shop/{id}")
    public String findArticle(@PathVariable int id, Model model, HttpSession session) {
        Optional<Article> article = articleService.findById(id);
        List<Category> categories = new ArrayList<Category>();
        if(article.isPresent())
        {
            // Plutôt ajouter les catégories dans ArticleInfo
            List<ArticleCategory> article_category = articleCategoryService.findArticleCategoriesByIdArticle(article.get().getId());
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
    public String findArticles(@RequestParam(required = false) Optional<String> categorie, Model model, HttpSession session) {
        List<Article> articles = new ArrayList<Article>();
        List<Category> lsCategories = new ArrayList<Category>();
        Category cat = null;
        if(!categorie.isPresent()) {
            articles = articleService.findAll();
        } else {
            List<ArticleCategory>  articlesCat;
            //Recherche d'un nom de catégorie correspondant dans la BDD
            Optional<Category> category = categoryService.findByName(categorie.get());
            if(category.isPresent()) {
                cat = category.get();
                //Recherche des entrées de cette catégorie contenant également un article
                articlesCat = articleCategoryService.findArticleCategoriesByNameCategory(cat.getNameCategory());
                for(ArticleCategory a : articlesCat) {
                    articles.add(a.getArticle());
                }
            }
        }

        for(Category c : categoryService.findAll()) {
            List<ArticleCategory> a_c = articleCategoryService.findArticleCategoriesByNameCategory(c.getNameCategory());
            if(!a_c.isEmpty()){
                lsCategories.add(c);
            }
        }

        model.addAttribute("categories", lsCategories);
        model.addAttribute("articles", utils.getArticlesInfo(articles,session));
        return "shop";
    }
}
