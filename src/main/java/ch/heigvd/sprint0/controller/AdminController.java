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

import javax.imageio.ImageIO;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.InputStream;
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

        if(error != null) {
            if ("DescAlreadyUsed".equals(error)) {
                error = "Cette description d'article est déjà utilisée par l'article " + errorMsg;
            }
            if ("NotAnImage".equals(error)) {
                error = "L'image n'est pas valide";
            }
        }

        List<Category> categories = (List<Category>) categoryRepository.findAll();
        model.addAttribute("selectedArticle", modelArticle); // article qui se trouve dans l'url
        model.addAttribute("categories", categories);
        model.addAttribute("article", new Article()); // article qui va être remplit dans le formulaire
        model.addAttribute("error", error);
        return "adminArticle.html";
    }

    @PostMapping("/admin/article")
    public String articleSubmit(@ModelAttribute Article article, Model model, @RequestParam(value = "image", required = false) MultipartFile image) {
        // Vérifier que la description n'est pas déjà utilisée par un autre article
        Optional<Article> articleWithSameDescription = articleService.findByDescription(article.getDescription());
        if(articleWithSameDescription.isPresent()) {
            return "redirect:/admin/article?error=DescAlreadyUsed&error_msg=" + articleWithSameDescription.get().getName();
        }

        if(!image.isEmpty()) {
            // On accepte que les images
            try (InputStream input = image.getInputStream()) {
                try { ImageIO.read(input).toString(); } catch (Exception e) {
                    // It's not an image.
                    return "redirect:/admin/article?error=NotAnImage";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            articleService.addArticle(article);
            Article insertedArticle = articleService.findTopByOrderByIdDesc().get(0);
            // Upload de l'image
            try {
                Path path = Paths.get("uploads");
                if(!Files.exists(path))
                    Files.createDirectory(path);

                String extension = "." + image.getOriginalFilename().split("\\.")[1];
                Files.copy(image.getInputStream(), path.resolve(insertedArticle.getId().toString() + extension));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // ajout d'un article sans image
            articleService.addArticle(article);
        }

        return "redirect:/admin";
    }
}
