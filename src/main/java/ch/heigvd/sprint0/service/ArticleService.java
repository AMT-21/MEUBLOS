package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.CartArticle;
import ch.heigvd.sprint0.repository.ArticleRepository;
import ch.heigvd.sprint0.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


//ArticleService contains the implementation of the findAll() method. We use the repository to retrieve data from
// the database.
@Service
public class ArticleService implements IArticleService {
    //ArticleRepository is injected
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
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
    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void deleteById(Integer idArticle) {
        articleRepository.deleteById(idArticle);
    }
}
