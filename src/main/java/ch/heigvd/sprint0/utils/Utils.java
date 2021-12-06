package ch.heigvd.sprint0.utils;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Cart;
import ch.heigvd.sprint0.model.Cart_Article;
import ch.heigvd.sprint0.object.ArticleInfo;
import ch.heigvd.sprint0.repository.CartArticleRepository;
import ch.heigvd.sprint0.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class Utils {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartArticleRepository cartArticleRepository;

    public List<ArticleInfo> getArticlesInfo(List<Article> articles, HttpSession session) {
        LinkedList<ArticleInfo> articleInfos = new LinkedList<>();
        for (Article article: articles) {
            articleInfos.add(getArticleInfo(article, session));
        }
        return articleInfos;
    }

    public ArticleInfo getArticleInfo(Article article, HttpSession session) {
        List<Cart_Article> cart_articles = (List<Cart_Article>) session.getAttribute("articles_in_cart");
        for (Cart_Article cart_article: cart_articles) {
            if (cart_article.getArticle().getId().equals(article.getId())) {
                return new ArticleInfo(article, cart_article);
            }
        }
        return new ArticleInfo(article, null);
    }

    // Efface le panier temporaire. (Si jamais cela peut-être utile pour des tests ou des requirement future)
    public void dropCart(HttpSession session) {
        session.removeAttribute("articles_in_cart");
        session.setAttribute("articles_in_cart", new LinkedList<>());
    }

    // Combine le panier temporaire avec le panier de l'utilisateur. Si pas d'utilisateur alors rien ne se passe.
    public void mergeCarts(HttpSession session) {

        // Récupère le panier de l'utilisateur si connecté.
        String cartId = (String) session.getAttribute("userId");
        if (cartId != null) {
            List<Cart_Article> cart_articles = (List<Cart_Article>) session.getAttribute("articles_in_cart");

            // Met à jour le panier temporaire pour lier tout les articles à l'utilisateur
            Cart cartUser = cartRepository.findById(cartId).get();
            for (Cart_Article c_a: cart_articles) {
                c_a.setCart(cartUser);
            }

            // Rajoute les articles du panier temporaire dans la DB.
            cartArticleRepository.saveAll(cart_articles);

            // Met à jour la session
            session.setAttribute("articles_in_cart", cartArticleRepository.findCart_ArticlesByIds_Cart_IdUser(cartId));
        }
    }
}
