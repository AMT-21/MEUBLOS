package ch.heigvd.sprint0.repository;

import ch.heigvd.sprint0.model.CartArticle;
import ch.heigvd.sprint0.model.CartArticleIds;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartArticleRepository extends CrudRepository<CartArticle, CartArticleIds> {
    List<CartArticle> findByIds_Cart_IdUser(String idUser);
    Optional<CartArticle> findByIds_Article_IdAndIds_Cart_IdUser(Integer idArticle, String idCart);
    void deleteByIds_Article_IdAndIds_Cart_IdUser(Integer idArticle, String idCart);
    void deleteByIds_Cart_IdUser(String idCart);
    void deleteByIds_Article_Id(Integer idArticle);
}
