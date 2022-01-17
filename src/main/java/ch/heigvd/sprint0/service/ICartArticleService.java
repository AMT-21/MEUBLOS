package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.CartArticle;

import java.util.List;
import java.util.Optional;

public interface ICartArticleService {
    List<CartArticle> findAllByIdCart(String idCart);
    Optional<CartArticle> find(Integer idArticle, String idCart);
    void save(CartArticle cartArticle);
    void saveAll(List<CartArticle> cartArticles);
    void delete(Integer idArticle, String idCart);
    void deleteAllByIdCart(String idCart);
    void deleteAllByIdArticle(Integer idArticle);
}
