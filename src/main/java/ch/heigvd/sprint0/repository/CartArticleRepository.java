package ch.heigvd.sprint0.repository;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Cart;
import ch.heigvd.sprint0.model.Cart_Article;
import ch.heigvd.sprint0.model.Cart_Article_Ids;
import org.springframework.data.repository.CrudRepository;

public interface CartArticleRepository extends CrudRepository<Cart_Article, Cart_Article_Ids> {
    Cart_Article findCart_ArticleByArticleAndCart(Article article, Cart cart);
}
