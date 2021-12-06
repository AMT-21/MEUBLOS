package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.ArticleCategory;
import ch.heigvd.sprint0.model.ArticleCategoryIds;
import ch.heigvd.sprint0.model.Category;
import ch.heigvd.sprint0.repository.ArticleCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCategoryService implements IArticleCategoryService {

    final ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    public ArticleCategoryService(ArticleCategoryRepository articleCategoryRepository) {
        this.articleCategoryRepository = articleCategoryRepository;
    }

    @Override
    public List<ArticleCategory> findArticleCategoriesByIdArticle(Integer idArticle) {
        return articleCategoryRepository.findArticleCategoriesByIds_Article_Id(idArticle);
    }

    @Override
    public List<ArticleCategory> findArticleCategoriesByNameCategory(String nameCategory) {
        return articleCategoryRepository.findArticleCategoriesByIds_Category_NameCategory(nameCategory);
    }

    @Override
    public void delete(Integer idArticle, String nameCategory) {
        articleCategoryRepository.deleteArticleCategoryByIds_Article_IdAndIds_Category_NameCategory(idArticle, nameCategory);
    }

    @Override
    public void delete(ArticleCategory articleCategory) {
        articleCategoryRepository.delete(articleCategory);
    }

    @Override
    public void deleteAll(Integer idArticle) {
        articleCategoryRepository.deleteArticleCategoriesByIds_Article_Id(idArticle);
    }


}
