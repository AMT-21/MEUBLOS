package ch.heigvd.sprint0.repository;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.ArticleCategory;
import ch.heigvd.sprint0.model.ArticleCategoryIds;
import ch.heigvd.sprint0.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//By extending from the Spring CrudRepository, we will have some methods for our data repository implemented,
//including findAll(). This way we save a lot of boilerplate code.
public interface ArticleCategoryRepository extends CrudRepository<ArticleCategory, ArticleCategoryIds> {
    List<ArticleCategory> findArticleCategoriesByIds_Article_Id(Integer idArticle);
    List<ArticleCategory> findArticleCategoriesByIds_Category_NameCategory(String nameCategory);
    void deleteArticleCategoryByIds_Article_IdAndIds_Category_NameCategory(Integer idArticle, String nameCategory);
    void deleteArticleCategoriesByIds_Article_Id(Integer idArticle);
}
