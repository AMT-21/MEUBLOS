package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Category;
import ch.heigvd.sprint0.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


//ArticleService contains the implementation of the findAll() method. We use the repository to retrieve data from
// the database.
@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //The findAll() method of the repository returns the list of articles
    @Override
    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findByName(String nomCategory) {
        return categoryRepository.findByNameCategory(nomCategory);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(String nameCategory) {
        categoryRepository.deleteById(nameCategory);
    }
}
