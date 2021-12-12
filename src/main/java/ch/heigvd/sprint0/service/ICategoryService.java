package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Category;

import java.util.List;
import java.util.Optional;

//IArticleService provides the findAll() contract method declaration to get all articles from the data source.
public interface ICategoryService {
    List<Category> findAll();
    List<Category> findAllIfUsed(); // Cherche les catégories qui sont attachées à des articles
    Optional<Category> findByName(String nomCategory);
    void save(Category category);
    void delete(String nameCategory);
}
