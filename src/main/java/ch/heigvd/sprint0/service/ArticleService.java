package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


//ArticleService contains the implementation of the findAll() method. We use the repository to retrieve data from
// the database.
@Service
public class ArticleService implements IArticleService {
    //ArticleRepository is injected
    @Autowired
    private ArticleRepository repository;

    //The findAll() method of the repository returns the list of articles
    @Override
    public List<Article> findAll()
    {
        return (List<Article>) repository.findAll();
    }

    @Override
    public Optional<Article> find(Long id){ return repository.findById(id);}
}
