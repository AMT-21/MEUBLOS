package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.ArticleCategory;

import java.util.List;

public interface IArticleCategoryService {
    List<ArticleCategory> findAll();
    List<ArticleCategory> findAllByArticle(Integer idArticle);
    List<ArticleCategory> findAllByCategory(String nameCategory);
    void delete(Integer idArticle, String nameCategory);
    void delete(ArticleCategory articleCategory);
    void deleteAllByArticle(Integer idArticle);
    void deleteAllByCategory(String nameCategory);
}
