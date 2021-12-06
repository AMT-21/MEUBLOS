package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.ArticleCategory;

import java.util.List;

public interface IArticleCategoryService {
    List<ArticleCategory> findArticleCategoriesByIdArticle(Integer idArticle);
    List<ArticleCategory> findArticleCategoriesByNameCategory(String nameCategory);
    void delete(Integer idArticle, String nameCategory);
    void delete(ArticleCategory articleCategory);
    void deleteAll(Integer idArticle);
}
