package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.CartArticle;
import ch.heigvd.sprint0.repository.CartArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<CartArticle> findAllByIdCart(String idCart) {
        return cartArticleRepository.findByIds_Cart_IdUser(idCart);
    }

    @Override
    public Optional<CartArticle> find(Integer idArticle, String idCart) {
        return cartArticleRepository.findByIds_Article_IdAndIds_Cart_IdUser(idArticle, idCart);
    }

    @Override
    public void save(CartArticle cartArticle) {
        cartArticleRepository.save(cartArticle);
    }

    @Override
    public void saveAll(List<CartArticle> cartArticles) {
        cartArticleRepository.saveAll(cartArticles);
    }

    @Transactional
    @Override
    public void delete(Integer idArticle, String idCart) {
        cartArticleRepository.deleteByIds_Article_IdAndIds_Cart_IdUser(idArticle, idCart);
    }

    @Transactional
    @Override
    public void deleteAllByIdCart(String idCart) {
        cartArticleRepository.deleteByIds_Cart_IdUser(idCart);
    }

    @Transactional
    @Override
    public void deleteAllByIdArticle(Integer idArticle) {
        cartArticleRepository.deleteByIds_Article_Id(idArticle);
    }
}
