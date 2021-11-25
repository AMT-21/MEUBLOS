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
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public String adminArticle(Model model, @RequestParam(name = "id", required = false) String id,
                               @RequestParam(name = "error", required = false) String error,
                               @RequestParam(name = "error_msg", required = false) String errorMsg) {
        if(id != null && !id.chars().allMatch(Character::isDigit))
            return "redirect:/admin";

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

        if(error != null && errorMsg != null) {
            if ("DescAlreadyUsed".equals(error)) {
                error = "Cette description d'article est déjà utilisée par l'article " + errorMsg;
            }
        } else {
            error = null;
        }

        List<Category> categories = (List<Category>) categoryRepository.findAll();
        model.addAttribute("selectedArticle", modelArticle); // article qui se trouve dans l'url
        model.addAttribute("categories", categories);
        model.addAttribute("article", new Article()); // article qui va être remplit dans le formulaire
        model.addAttribute("error", error);
        return "adminArticle.html";
    }

    @PostMapping("/admin/article")
    public String articleSubmit(@ModelAttribute Article article, Model model, @RequestParam("image") MultipartFile image) {
        // Vérifier que la description n'est pas déjà utilisée par un autre article
        Optional<Article> articleWithSameDescription = articleService.findByDescription(article.getDescription());
        if(articleWithSameDescription.isPresent()) {
            return "redirect:/admin/article?error=DescAlreadyUsed&error_msg=" + articleWithSameDescription.get().getName();
        }

        articleService.addArticle(article);
        Article insertedArticle = articleService.findTopByOrderByIdDesc().get(0);

        try {
            // Get the file and save it somewhere
            byte[] bytes = image.getBytes();
            Path path = Paths.get("F://temp//" + insertedArticle.getId());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/admin";
    }
}
