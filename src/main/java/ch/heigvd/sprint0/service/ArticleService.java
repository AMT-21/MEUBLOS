package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Cart_Article;
import ch.heigvd.sprint0.repository.ArticleRepository;
import ch.heigvd.sprint0.repository.CartArticleRepository;
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
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CartRepository cartRepository;

    //The findAll() method of the repository returns the list of articles
    @Override
    public List<Article> findAll() {
        return (List<Article>) articleRepository.findAll();
    }

    public Optional<Article> findById(int id) {
        return articleRepository.findById(id);
    }

    public Optional<Article> findByDescription(String description) {
        return articleRepository.findByDescription(description);
    }

    public List<Article> findTopByOrderByIdDesc() {
        return articleRepository.findTopByOrderByIdDesc();
    }

    @Override
    public List<Cart_Article> findCartArticleFromUser(int id) {
        return new LinkedList<>(cartRepository.findById(id).get().getCart_article_list());
    }

    @Override
    public void saveArticle(Article article) {
        articleRepository.save(article);
    }
}
