package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Article_Category;
import ch.heigvd.sprint0.model.Article_Category_Ids;
import ch.heigvd.sprint0.model.Cart_Article;

import java.util.List;
import java.util.Optional;

//IArticleService provides the findAll() contract method declaration to get all cities from the data source.
public interface IArticleService {
    List<Article> findAll();
    Optional<Article> findById(int id);
    Optional<Article> findByDescription(String description);
    List<Cart_Article> findCartArticleFromUser(String id);
    void saveArticle(Article article);
    List<Article> findTopByOrderByIdDesc();
    void deleteArticle(Article article);
}
