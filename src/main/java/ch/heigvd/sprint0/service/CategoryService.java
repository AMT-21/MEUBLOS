package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.ArticleCategory;
import ch.heigvd.sprint0.model.Category;
import ch.heigvd.sprint0.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//ArticleService contains the implementation of the findAll() method. We use the repository to retrieve data from
// the database.
@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    private final IArticleCategoryService articleCategoryService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ArticleCategoryService articleCategoryService) {
        this.categoryRepository = categoryRepository;
        this.articleCategoryService = articleCategoryService;
    }

    //The findAll() method of the repository returns the list of articles
    @Override
    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllIfUsed() {
        return articleCategoryService.findAll()
                .stream()
                .map(ArticleCategory::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findByName(String nomCategory) {
        return categoryRepository.findByNameCategory(nomCategory);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void delete(String nameCategory) {
        articleCategoryService.deleteAllByCategory(nameCategory);
        categoryRepository.deleteById(nameCategory);
    }
}
