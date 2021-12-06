package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.CartArticle;

import java.util.List;
import java.util.Optional;

public interface ICartArticleService {
    List<CartArticle> findCartArticlesByIdCart(String idCart);
    Optional<CartArticle> findCartArticleByIdArticleAndIdCart(Integer idArticle, String idCart);
    void save(CartArticle cartArticle);
    void saveAll(List<CartArticle> cartArticles);
    void deleteCartArticleByIdArticleAndIdCart(Integer idArticle, String idCart);
    void deleteCartArticlesByIdCart(String idCart);
}
