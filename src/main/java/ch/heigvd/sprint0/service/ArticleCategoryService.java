package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.ArticleCategory;
import ch.heigvd.sprint0.repository.ArticleCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleCategoryService implements IArticleCategoryService {

    final ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    public ArticleCategoryService(ArticleCategoryRepository articleCategoryRepository) {
        this.articleCategoryRepository = articleCategoryRepository;
    }

    @Override
    public List<ArticleCategory> findAll() {
        return (List<ArticleCategory>) articleCategoryRepository.findAll();
    }

    @Override
    public List<ArticleCategory> findAllByArticle(Integer idArticle) {
        return articleCategoryRepository.findByIds_Article_Id(idArticle);
    }

    @Override
    public List<ArticleCategory> findAllByCategory(String nameCategory) {
        return articleCategoryRepository.findByIds_Category_NameCategory(nameCategory);
    }

    @Transactional
    @Override
    public void delete(Integer idArticle, String nameCategory) {
        articleCategoryRepository.deleteByIds_Article_IdAndIds_Category_NameCategory(idArticle, nameCategory);
    }

    @Transactional
    @Override
    public void delete(ArticleCategory articleCategory) {
        articleCategoryRepository.delete(articleCategory);
    }

    @Transactional
    @Override
    public void deleteAllByArticle(Integer idArticle) {
        articleCategoryRepository.deleteByIds_Article_Id(idArticle);
    }

    @Transactional
    @Override
    public void deleteAllByCategory(String nameCategory) {
        articleCategoryRepository.deleteByIds_Category_NameCategory(nameCategory);
    }


}
