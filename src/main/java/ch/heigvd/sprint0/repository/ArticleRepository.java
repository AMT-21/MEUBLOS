package ch.heigvd.sprint0.repository;

import ch.heigvd.sprint0.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//By extending from the Spring CrudRepository, we will have some methods for our data repository implemented,
//including findAll(). This way we save a lot of boilerplate code.
public interface ArticleRepository extends CrudRepository<Article, Integer> {
    List<Article> findTop3ByOrderByIdDesc();
}
