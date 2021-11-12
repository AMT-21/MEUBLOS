package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Article;

import java.util.List;

//IArticleService provides the findAll() contract method declaration to get all cities from the data source.
public interface IArticleService {
    List<Article> findAll();
}
