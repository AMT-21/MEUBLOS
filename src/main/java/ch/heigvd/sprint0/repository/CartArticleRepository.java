package ch.heigvd.sprint0.repository;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Cart_Article;
import ch.heigvd.sprint0.model.Cart_Article_Ids;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartArticleRepository extends CrudRepository<Cart_Article, Cart_Article_Ids> {
    List<Cart_Article> findCart_ArticlesByIds_Cart_IdUser(String ids_cart_idUser);
    Cart_Article findCart_ArticleByIds_Article_IdAndIds_Cart_IdUser(Integer ids_article_id, String ids_cart_idUser);
    void deleteCart_ArticleByIds_Article_IdAndIds_Cart_IdUser(Integer ids_article_id, String ids_cart_idUser);
    void deleteCart_ArticlesByIds_Cart_IdUser(String idUser);
}
