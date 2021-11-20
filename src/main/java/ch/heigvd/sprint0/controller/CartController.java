package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Cart;
import ch.heigvd.sprint0.model.Cart_Article;
import ch.heigvd.sprint0.model.Cart_Article_Ids;
import ch.heigvd.sprint0.repository.ArticleRepository;
import ch.heigvd.sprint0.repository.CartArticleRepository;
import ch.heigvd.sprint0.repository.CartRepository;
import ch.heigvd.sprint0.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private IArticleService articleService;

    @Autowired
    private CartArticleRepository cartArticleRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/cart")
    public String index(Model model, HttpSession session) {

        // Put in session the content of the cart.
        getCartArticles(session);

        return "cart.html";
    }

    @GetMapping("/cart/add/{artId}")
    public String add(Model model, HttpSession session, @PathVariable("artId") int artId) {

        // Si l'utilisateur est connecté.
        Integer cartId = (Integer) session.getAttribute("userId");
        boolean isConnected = cartId != null;

        List<Cart_Article> cart_articles;
        Cart cart = isConnected ? cartRepository.findById(cartId).get() : null;
        Article article = articleRepository.findById(artId).get();
        Cart_Article_Ids ids = new Cart_Article_Ids(cart, article);

        Cart_Article objToAdd = new Cart_Article(ids, 1);

        // Rajoute l'article au panier de l'utilisateur ou dans le panier de la session
        if (isConnected) {
            cartArticleRepository.save(objToAdd);
            cart_articles = articleService.findCartArticleFromUser(cartId);
        } else { // sinon il faut traité le panier de la session.
            cart_articles = (List<Cart_Article>) session.getAttribute("articles_in_cart");
            cart_articles.add(objToAdd);
        }

        // Mettre à jour la session.
        session.setAttribute("articles_in_cart", cart_articles);

        return "redirect:/cart";
        //return new ResponseEntity(HttpStatus.OK).toString();
    }

    @GetMapping("/cart/remove/{artId}")
    public String remove(Model model, HttpSession session, @PathVariable("artId") int artId) {

        Integer cartId = (Integer) session.getAttribute("userId");
        boolean isConnected = cartId != null;

        List<Cart_Article> cart_articles = getCartArticles(session);

        // Si l'utilisateur est connecté. Enlever l'article du panier.
        if (isConnected) {
            Cart cart = cartRepository.findById(cartId).get();
            Article article = articleRepository.findById(artId).get();
            Cart_Article_Ids ids = new Cart_Article_Ids(cart, article);
            cartArticleRepository.delete(cartArticleRepository.findById(ids).get());
            cart_articles = articleService.findCartArticleFromUser(cartId);
        } else { // sinon il faut juste effacé l'article du panier.
            cart_articles = (List<Cart_Article>) session.getAttribute("articles_in_cart");
            for(Cart_Article c : cart_articles){
                if ( c.getArticle().getId() == artId ){
                    cart_articles.remove(c);
                    break;
                }
            }
        }

        // Mettre à jour la session.
        session.setAttribute("articles_in_cart", cart_articles);

        return "redirect:/cart";
    }

    // Récupère le contenu du panier si déjà dans la session. Sinon la crée
    private List<Cart_Article> getCartArticles(HttpSession session) {
        List<Cart_Article> cart_articles = (List<Cart_Article>) session.getAttribute("articles_in_cart");
        if (cart_articles == null) { // Si session vide
            cart_articles = new LinkedList<>();

            // Récupère le panier de l'utilisateur si connecté.
            Integer cartId = (Integer) session.getAttribute("userId");
            if (cartId != null) {
                cart_articles.addAll(articleService.findCartArticleFromUser(cartId));
            }

            // Met à jour la session
            session.setAttribute("articles_in_cart", cart_articles);
        }

        return cart_articles;
    }
}
