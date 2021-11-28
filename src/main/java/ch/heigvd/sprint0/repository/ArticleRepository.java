package ch.heigvd.sprint0.repository;

import ch.heigvd.sprint0.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//By extending from the Spring CrudRepository, we will have some methods for our data repository implemented,
//including findAll(). This way we save a lot of boilerplate code.
public interface ArticleRepository  extends CrudRepository<Article, Long> {
    // @Query("SELECT m FROM Article m INNER JOIN meuble_categorie AS mc INNER JOIN categorie WHERE m.id = mc.idMeuble AND mc.categorie = :categorie")
    @Query("SELECT e  FROM Article e")
    List<Article> findByFilter(@Param("categorie") String categorie);
}
