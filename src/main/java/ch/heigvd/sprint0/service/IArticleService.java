package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.CartArticle;

import java.util.List;
import java.util.Optional;

public interface IArticleService {
    List<Article> findAll();
    Optional<Article> findById(Integer id);
    Optional<Article> findByDescription(String description);
    Optional<Article> findLatestArticle();
    List<Article> find3LatestArticles();
    List<Article> findLatestArticles();
    List<Article> findAllWithCategory(String nameCategory);
    void saveArticle(Article article);
    void deleteById(Integer idArticle);
}
