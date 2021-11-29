package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Article_Category;
import ch.heigvd.sprint0.model.Category;
import ch.heigvd.sprint0.object.ArticleInfo;
import ch.heigvd.sprint0.repository.ArticleCategoryRepository;
import ch.heigvd.sprint0.repository.CategoryRepository;
import ch.heigvd.sprint0.service.IArticleService;
import ch.heigvd.sprint0.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class ShopController {
    @Autowired
    private Utils utils;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ch.heigvd.sprint0.repository.ArticleCategoryRepository ArticleCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/shop/{id}")
    public String findArticle(@PathVariable int id, Model model, HttpSession session) {
        Optional<Article> article = articleService.findById(id);
        List<Category> categories = new ArrayList<Category>();
        if(article.isPresent())
        {
            // Plutôt ajouter les catégories dans ArticleInfo
            List<Article_Category> article_category = ArticleCategoryRepository.findArticle_CategoriesByIds_Article(article.get());
            for(Article_Category a_c : article_category)
            {
                categories.add(a_c.getCategory());
            }
            model.addAttribute("categories", categories);
            model.addAttribute("article", utils.getArticleInfo(article.get(),session));
        }
        return "article_detail.html";
    }

    @GetMapping("/shop")
    public String findArticles(@RequestParam(required = false) Optional<String> categorie, Model model, HttpSession session) {
        List<Article> articles = new ArrayList<Article>();
        List<Category> lsCategories = new ArrayList<Category>();
        Category cat = null;
        if(!categorie.isPresent()) {
            articles = articleService.findAll();
        }
        else
        {
            List<Article_Category>  articlesCat;
            //Recherche d'un nom de catégorie correspondant dans la BDD
            Optional<Category> category = categoryRepository.findByNameCategory(categorie.get());
            if(category.isPresent())
            {
                cat = category.get();
            }
            //Recherche des entrées de cette catégorie contenant également un article
            articlesCat = ArticleCategoryRepository.findArticle_CategoriesByIds_Category(cat);
            for(Article_Category a : articlesCat)
            {
                articles.add(a.getArticle());
            }
        }

        for(Category c : categoryRepository.findAll())
        {
            List<Article_Category> a_c = ArticleCategoryRepository.findArticle_CategoriesByIds_Category(c);
            if(!a_c.isEmpty()){
                lsCategories.add(c);
            }
        }

        model.addAttribute("categories", lsCategories);
        model.addAttribute("articles", utils.getArticlesInfo(articles,session));
        return "shop.html";
    }
}
