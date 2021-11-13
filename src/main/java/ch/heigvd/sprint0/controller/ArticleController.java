package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    /*@PostMapping("/articles/{id}")
    public ResponseEntity<Optional<Article>> findArticle(@PathVariable Long id) {
        Optional<Article> article = articleService.findById(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }*/
}
