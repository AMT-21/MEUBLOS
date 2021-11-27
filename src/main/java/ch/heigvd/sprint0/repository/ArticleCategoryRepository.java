package ch.heigvd.sprint0.repository;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Article_Category;
import ch.heigvd.sprint0.model.Article_Category_Ids;
import ch.heigvd.sprint0.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//By extending from the Spring CrudRepository, we will have some methods for our data repository implemented,
//including findAll(). This way we save a lot of boilerplate code.
public interface ArticleCategoryRepository extends CrudRepository<Article_Category, Article_Category_Ids> {
    List<Article_Category> findArticle_CategoriesByIds_Article(Article ids_article);
    List<Article_Category> findArticle_CategoriesByIds_Category(Category category);
}
