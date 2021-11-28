package ch.heigvd.sprint0.repository;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//By extending from the Spring CrudRepository, we will have some methods for our data repository implemented,
//including findAll(). This way we save a lot of boilerplate code.
public interface CategoryRepository extends CrudRepository<Category, String> {
    public Optional<Category> findByNameCategory(String nomCategorie);
}
