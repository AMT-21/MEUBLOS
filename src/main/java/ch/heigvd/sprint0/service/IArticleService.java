package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Cart_Article;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//IArticleService provides the findAll() contract method declaration to get all articles from the data source.
public interface IArticleService {
    List<Article> findAll();
    Optional<Article> findById(int id);
    Optional<Article> findByDescription(String description);
    List<Cart_Article> findCartArticleFromUser(int id);
    void saveArticle(Article article);
    List<Article> findTopByOrderByIdDesc();
    void deleteArticle(Article article);
}
