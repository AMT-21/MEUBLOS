package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.ArticleCategory;
import ch.heigvd.sprint0.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//ArticleService contains the implementation of the findAll() method. We use the repository to retrieve data from
// the database.
@Service
public class ArticleService implements IArticleService {

    private final ArticleRepository articleRepository;

    private final IArticleCategoryService articleCategoryService;

    private final ICartArticleService cartArticleService;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, IArticleCategoryService articleCategoryService, ICartArticleService cartArticleService) {
        this.articleRepository = articleRepository;
        this.articleCategoryService = articleCategoryService;
        this.cartArticleService = cartArticleService;
    }

    //The findAll() method of the repository returns the list of articles
    @Override
    public List<Article> findAll() {
        return (List<Article>) articleRepository.findAll();
    }

    public Optional<Article> findById(Integer id) {
        return articleRepository.findById(id);
    }

    public Optional<Article> findByDescription(String description) {
        return articleRepository.findByDescription(description);
    }

    @Override
    public Optional<Article> findLatestArticle() {
        return articleRepository.findTop1ByOrderByIdDesc();
    }

    @Override
    public List<Article> find3LatestArticles() {
        return articleRepository.findTop3ByOrderByIdDesc();
    }

    public List<Article> findLatestArticles() {
        return articleRepository.findTopByOrderByIdDesc();
    }

    @Override
    public List<Article> findAllWithCategory(String nameCategory) {
        return articleCategoryService.findAllByCategory(nameCategory)
                .stream()
                .map(ArticleCategory::getArticle)
                .collect(Collectors.toList());
    }

    @Override
    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    @Transactional
    @Override
    public void deleteById(Integer idArticle) {
        articleCategoryService.deleteAllByArticle(idArticle);
        cartArticleService.deleteAllByIdArticle(idArticle);
        articleRepository.deleteById(idArticle);
    }
}
