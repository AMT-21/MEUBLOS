package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.ArticleCategory;
import ch.heigvd.sprint0.model.Category;
import ch.heigvd.sprint0.service.IArticleCategoryService;
import ch.heigvd.sprint0.service.IArticleService;
import ch.heigvd.sprint0.service.ICategoryService;
import ch.heigvd.sprint0.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Controller
public class AdminController {

    private final IArticleService articleService;

    private final IArticleCategoryService articleCategoryService;

    private final ICategoryService categoryService;

    private final SessionService sessionService;

    @Value("${server.tomcat.upload-dir}")
    private String uploadPath;

    @Autowired
    public AdminController(IArticleService articleService, IArticleCategoryService articleCategoryService, ICategoryService categoryService, SessionService sessionService) {
        this.articleService = articleService;
        this.articleCategoryService = articleCategoryService;
        this.categoryService = categoryService;
        this.sessionService = sessionService;
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpServletRequest request, HttpServletResponse response) {
        checkAdminAccess(request, response);
        List<Article> articles = articleService.findAll();

        model.addAttribute("articles", articles);
        return "admin";
    }

    @GetMapping("/admin/article")
    public String adminArticle(Model model, @RequestParam(name = "id", required = false) String id,
                               @RequestParam(name = "error", required = false) String error,
                               @RequestParam(name = "error_msg", required = false) String errorMsg) {
        //checkAdminAccess(request, response);
        if(id != null && !id.chars().allMatch(Character::isDigit))
            return admin(model);


        Article modelArticle = null;
        if (id != null) {
            modelArticle = articleService.findById(Integer.parseInt(id)).orElse(null);
        }

        if(error != null) {
            if ("DescAlreadyUsed".equals(error)) {
                error = "Cette description d'article est déjà utilisée par l'article " + errorMsg;
            }
            if ("NotAnImage".equals(error)) {
                error = "L'image n'est pas valide";
            }
        }

        List<Category> categories = categoryService.findAll();
        if(modelArticle != null)
            model.addAttribute("article", modelArticle); // article qui se trouve dans l'url
        else
            model.addAttribute("article", new Article()); // article vide qui va être remplit dans le formulaire
        model.addAttribute("categories", categories);
        model.addAttribute("error", error);
        return "adminArticle";
    }

    @PostMapping("/admin/article")
    public String articleSubmit(@ModelAttribute Article article, Model model,
                                @RequestParam(value = "image", required = false) MultipartFile image,
                                @RequestParam(value = "article_category_list", required = false) String article_category_list, HttpServletRequest request, HttpServletResponse response) {
        checkAdminAccess(request, response);
        Optional<Article> articleWithSameDescription = articleService.findByDescription(article.getDescription());
        // C'est une modification d'article
        if(article.getId() != null) {
            // Vérifier que la description n'est pas déjà utilisée par un autre article
            if(articleWithSameDescription.isPresent() && !articleWithSameDescription.get().getId().equals(article.getId())) {
                return adminArticle(model, null, "DescAlreadyUsed", articleWithSameDescription.get().getName());
            }

            if(article_category_list == null) {
                // aucune catégorie cochée, on les supprime tous si elles existent
                articleCategoryService.deleteAllByArticle(article.getId());
            } else {
                // supprimer les catégories qui ne sont pas cochées
                List<Category> allCats = categoryService.findAll();
                String[] articleCats = article_category_list.split(",");
                for(Category c : allCats) {
                    // si la catégorie n'appartient pas à l'article
                    if(!Arrays.asList(articleCats).contains(c.getNameCategory())) {
                        // si la catégorie est une ancienne catégorie de l'article
                        for(ArticleCategory articleAc : articleCategoryService.findAllByArticle(article.getId())) {
                            if(articleAc.getCategory().getNameCategory().equals(c.getNameCategory())) {
                                articleCategoryService.delete(article.getId(), c.getNameCategory());
                            }
                        }
                    }
                }
            }
        } else {    // C'est un ajout d'article
            // Vérifier que la description n'est pas déjà utilisée par un autre article
            if(articleWithSameDescription.isPresent()) {
                return adminArticle(model, null, "DescAlreadyUsed", articleWithSameDescription.get().getName());
            }
        }

        articleService.saveArticle(article);

        if(!image.isEmpty()) {
            // On accepte que les images
            try (InputStream input = image.getInputStream()) {
                try { ImageIO.read(input).toString(); } catch (Exception e) {
                    // It's not an image.
                    return adminArticle(model,null, "NotAnImage", null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Récupère l'id du nouvel élément pour l'utiliser comme nom d'image
            Article insertedArticle = article.getId() != null ? article : articleService.findLatestArticle().get();

            // Upload de l'image
            try {
                String extension = "." + image.getOriginalFilename().split("\\.")[image.getOriginalFilename().split("\\.").length - 1];
                // On accepte que les .jpg
                if(!extension.equals(".jpg")) {
                    return adminArticle(model,null, "NotAnImage", null);
                }

                if (!Files.exists(Paths.get(uploadPath))) {
                    Files.createDirectories(Paths.get(uploadPath));
                }

                Path destinationFile = Paths.get(uploadPath).resolve(
                                Paths.get(insertedArticle.getId().toString() + extension)).normalize().toAbsolutePath();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, destinationFile,
                            StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/admin";
    }

    @GetMapping("/admin/article/delete")
    public String articleDelete(@RequestParam(name = "id") String id, HttpServletRequest request, HttpServletResponse response) {
        checkAdminAccess(request, response);
        // L'id existe ?
        Optional<Article> article = articleService.findById(Integer.parseInt(id));
        article.ifPresent(value -> articleService.deleteById(value.getId()));

        return "redirect:/admin";
    }

    @GetMapping("/admin/categories")
    public String adminCategories(Model model,
                                  @RequestParam(name = "error", required = false) String error, HttpServletRequest request, HttpServletResponse response) {
        checkAdminAccess(request, response);
        List<Category> categories = categoryService.findAll();
        if(!categories.isEmpty()) {
            model.addAttribute("categories", categories);
        }

        model.addAttribute("category", new Category());
        model.addAttribute("error", error);
        return "adminCategories";
    }

    @PostMapping("/admin/categories")
    public String categorySubmit(Model model, @ModelAttribute Category category, HttpServletRequest request, HttpServletResponse response) {
        checkAdminAccess(request, response);
        // Vérifier que le nom de la catégorie existe pas déjà
        Optional<Category> cat = categoryService.findByName(category.getNameCategory());
        if(cat.isPresent()) {
            return adminCategories(model, "catAlreadyExists");
        }

        categoryService.save(new Category(category.getNameCategory()));
        return adminCategories(model, null);
    }

    @GetMapping("/admin/categories/confirmDeletion")
    public String confirmCategoryDeletion(Model model, @RequestParam(name = "id") String id, HttpServletRequest request, HttpServletResponse response) {
        checkAdminAccess(request, response);
        // L'id existe ?
        Optional<Category> category = categoryService.findByName(id);
        if(category.isPresent()) {
            // Récupérer la liste des articles impactés
            List<ArticleCategory> acs = articleCategoryService.findAllByCategory(id);
            if(acs.isEmpty()) {
                categoryService.delete(id);
                return adminCategories(model, null);
            }
            List<Article> concernedArticles = new ArrayList<>();
            for(ArticleCategory ac : acs) {
                concernedArticles.add(ac.getArticle());
            }

            model.addAttribute("articles", concernedArticles);
            model.addAttribute("category", category.get());
            return "adminCategoriesConfirmDeletion";
        }

        return adminCategories(model, null);
    }

    @GetMapping("/admin/categories/delete")
    public String categoryDelete(Model model, @RequestParam(name = "id") String id, HttpServletRequest request, HttpServletResponse response) {
        checkAdminAccess(request, response);
        // L'id existe ?
        Optional<Category> category = categoryService.findByName(id);
        if(category.isPresent()) {
            categoryService.delete(id);
        }

        return adminCategories(model,null);
    }


    private void checkAdminAccess(HttpServletRequest request, HttpServletResponse response) {
        String[] userData = sessionService.checkLogin(request);
        if(!(userData != null && userData[1].equals("admin"))) {    // Etre logué + rôle admin pour accéder à la page
            try {
                response.sendRedirect("./");                     // Redirection, access denied
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
