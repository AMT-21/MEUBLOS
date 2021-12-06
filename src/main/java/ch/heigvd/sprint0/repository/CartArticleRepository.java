package ch.heigvd.sprint0.repository;

import ch.heigvd.sprint0.model.CartArticle;
import ch.heigvd.sprint0.model.CartArticleIds;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartArticleRepository extends CrudRepository<CartArticle, CartArticleIds> {
    List<CartArticle> findCartArticlesByIds_Cart_IdUser(String idUser);
    Optional<CartArticle> findCartArticleByIds_Article_IdAndIds_Cart_IdUser(Integer idArticle, String idCart);
    void deleteCartArticleByIds_Article_IdAndIds_Cart_IdUser(Integer idArticle, String idCart);
    void deleteCartArticlesByIds_Cart_IdUser(String idCart);
}
