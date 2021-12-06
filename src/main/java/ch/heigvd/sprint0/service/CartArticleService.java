package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Cart;
import ch.heigvd.sprint0.model.CartArticle;
import ch.heigvd.sprint0.repository.CartArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartArticleService implements ICartArticleService {

    final CartArticleRepository cartArticleRepository;

    @Autowired
    public CartArticleService(CartArticleRepository cartArticleRepository) {
        this.cartArticleRepository = cartArticleRepository;
    }

    @Override
    public List<CartArticle> findCartArticlesByIdCart(String idCart) {
        return cartArticleRepository.findCartArticlesByIds_Cart_IdUser(idCart);
    }

    @Override
    public Optional<CartArticle> findCartArticleByIdArticleAndIdCart(Integer idArticle, String idCart) {
        return cartArticleRepository.findCartArticleByIds_Article_IdAndIds_Cart_IdUser(idArticle, idCart);
    }

    @Override
    public void save(CartArticle cartArticle) {
        cartArticleRepository.save(cartArticle);
    }

    @Override
    public void saveAll(List<CartArticle> cartArticles) {
        cartArticleRepository.saveAll(cartArticles);
    }

    @Override
    public void deleteCartArticleByIdArticleAndIdCart(Integer idArticle, String idCart) {
        cartArticleRepository.deleteCartArticleByIds_Article_IdAndIds_Cart_IdUser(idArticle, idCart);
    }

    @Override
    public void deleteCartArticlesByIdCart(String idCart) {
        cartArticleRepository.deleteCartArticlesByIds_Cart_IdUser(idCart);
    }
}
