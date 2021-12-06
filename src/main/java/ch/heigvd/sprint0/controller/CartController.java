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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/cart/add/{artId}")
    public String add(Model model, HttpSession session,
                      @PathVariable("artId") int artId,
                      @RequestParam("quantity") Integer quantity) {

        // Si l'utilisateur est connecté.
        String cartId = (String) session.getAttribute("userId");
        boolean isConnected = cartId != null;

        Cart cart = isConnected ? cartRepository.findById(cartId).get() : null;
        Article article = articleRepository.findById(artId).get();
        Cart_Article_Ids ids = new Cart_Article_Ids(cart, article);

        List<Cart_Article> cart_articles;

        Cart_Article objToAdd = new Cart_Article(ids, quantity);

        // Rajoute l'article au panier de l'utilisateur ou dans le panier de la session
        if (isConnected) {
            cartArticleRepository.save(objToAdd);
            cart_articles = cartArticleRepository.findCart_ArticlesByIds_Cart_IdUser(cartId);
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
    public String remove(Model model, HttpSession session,
                         @PathVariable("artId") int artId) {

        String cartId = (String) session.getAttribute("userId");
        boolean isConnected = cartId != null;

        List<Cart_Article> cart_articles;

        // Si l'utilisateur est connecté. Enlever l'article du panier.
        if (isConnected) {
            cartArticleRepository.deleteCart_ArticleByIds_Article_IdAndIds_Cart_IdUser(artId, cartId);
            cart_articles = cartArticleRepository.findCart_ArticlesByIds_Cart_IdUser(cartId);
        } else { // sinon il faut juste effacer l'article du panier.
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

    @GetMapping("/cart/removeAll")
    public String remove(Model model, HttpSession session) {

        String cartId = (String) session.getAttribute("userId");
        boolean isConnected = cartId != null;

        List<Cart_Article> cart_articles;

        // Si l'utilisateur est connecté. Enlever l'article du panier.
        if (isConnected) {
            cartArticleRepository.deleteCart_ArticlesByIds_Cart_IdUser(cartId);
            cart_articles = cartArticleRepository.findCart_ArticlesByIds_Cart_IdUser(cartId);
        }

        // Mettre à jour la session.
        session.setAttribute("articles_in_cart", new LinkedList<>());

        return "redirect:/cart";
    }

    @PostMapping("/cart/update/{artId}")
    public String updateQuantity(Model model, HttpSession session,
                                 @PathVariable("artId") int artId,
                                 @RequestParam("quantity") Integer quantity) {
        // Si l'utilisateur est connecté.
        String cartId = (String) session.getAttribute("userId");
        boolean isConnected = cartId != null;
        boolean toRemove = quantity < 1;

        if (toRemove) {
            return remove(model,session,artId);
        }

        List<Cart_Article> cart_articles;
        Cart_Article cart_article = null;

        // Si l'utilisateur est connecté. Mettre à jour son panier.
        if (isConnected) {
            cart_article = cartArticleRepository.findCart_ArticleByIds_Article_IdAndIds_Cart_IdUser(artId, cartId);

            if (cart_article == null) {
                add(model, session, artId, 1);
                cart_article = cartArticleRepository.findCart_ArticleByIds_Article_IdAndIds_Cart_IdUser(artId, cartId);
            }

            cart_article.setQuantity(quantity);
            cartArticleRepository.save(cart_article);   // update
            cart_articles = cartArticleRepository.findCart_ArticlesByIds_Cart_IdUser(cartId);
        } else { // sinon il faut traiter le panier de la session.
            cart_articles = (List<Cart_Article>) session.getAttribute("articles_in_cart");
            for(Cart_Article c : cart_articles){
                if ( c.getArticle().getId() == artId ){
                    cart_article = c;
                    c.setQuantity(quantity);
                    break;
                }
            }

            if (cart_article == null) {
                add(model, session, artId, 1);
                cart_articles = (List<Cart_Article>) session.getAttribute("articles_in_cart");
                for(Cart_Article c : cart_articles){
                    if ( c.getArticle().getId() == artId ){
                        c.setQuantity(quantity);
                        break;
                    }
                }
            }
        }

        // Mettre à jour la session.
        session.setAttribute("articles_in_cart", cart_articles);

        return "redirect:/cart";
    }

    // Récupère le contenu du panier si déjà dans la session. Sinon la crée ou l'ajoute à l'utilisateur connecté
    private List<Cart_Article> getCartArticles(HttpSession session) {
        List<Cart_Article> cart_articles = (List<Cart_Article>) session.getAttribute("articles_in_cart");
        if (cart_articles == null) { // Si session vide
            cart_articles = new LinkedList<>();

            // Récupère le panier de l'utilisateur si connecté.
            String cartId = (String) session.getAttribute("userId");
            if (cartId != null) {
                cart_articles.addAll(cartArticleRepository.findCart_ArticlesByIds_Cart_IdUser(cartId));
            }

            // Met à jour la session
            session.setAttribute("articles_in_cart", cart_articles);
        }

        return cart_articles;
    }
}
