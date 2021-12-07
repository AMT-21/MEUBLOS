package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Cart;
import ch.heigvd.sprint0.model.CartArticle;
import ch.heigvd.sprint0.model.CartArticleIds;
import ch.heigvd.sprint0.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Controller
public class CartController {
    private final IArticleService articleService;

    private final ICartArticleService cartArticleService;

    private final ICartService cartService;

    @Autowired
    public CartController(IArticleService articleService, ICartArticleService cartArticleService, ICartService cartService) {
        this.articleService = articleService;
        this.cartArticleService = cartArticleService;
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String index(HttpSession session) {

        // Met le contenu du panier dans la session.
        List<CartArticle> cart_articles = (List<CartArticle>) session.getAttribute("articles_in_cart");

        // Récupère le panier de l'utilisateur si connecté.
        String cartId = (String) session.getAttribute("userId");
        if (cartId != null) {
            cart_articles.addAll(cartArticleService.findAllByIdCart(cartId));
        }

        // Met à jour la session
        session.setAttribute("articles_in_cart", cart_articles);

        return "cart";
    }

    @PostMapping("/cart/add/{artId}")
    public String add(HttpSession session,
                      @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer,
                      @PathVariable("artId") int artId,
                      @RequestParam("quantity") Integer quantity) {

        // Si l'utilisateur est connecté.
        String cartId = (String) session.getAttribute("userId");

        Cart cart = null;
        if (cartId != null) {
            cart = cartService.findById(cartId).orElse(null);
        }
        Article article = articleService.findById(artId).orElse(null);

        if (article == null) { // Invalid article id.
            return "redirect:" + referrer;
        }

        List<CartArticle> cart_articles;
        CartArticleIds ids = new CartArticleIds(cart, article);
        CartArticle objToAdd = new CartArticle(ids, quantity);

        // Rajoute l'article au panier de l'utilisateur ou dans le panier de la session
        if (cart != null) {
            cartArticleService.save(objToAdd);
            cart_articles = cartArticleService.findAllByIdCart(cartId);
        } else { // sinon il faut traité le panier de la session.
            cart_articles = (List<CartArticle>) session.getAttribute("articles_in_cart");
            cart_articles.add(objToAdd);
        }

        // Mettre à jour la session.
        session.setAttribute("articles_in_cart", cart_articles);

        return "redirect:" + referrer;
    }

    @GetMapping("/cart/remove/{artId}")
    public String remove(HttpSession session,
                         @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer,
                         @PathVariable("artId") int artId) {

        String cartId = (String) session.getAttribute("userId");

        Cart cart = null;
        if (cartId != null) {
            cart = cartService.findById(cartId).orElse(null);
        }
        List<CartArticle> cart_articles;

        // Si l'utilisateur est connecté. Enlever l'article du panier.
        if (cart != null) {
            cartArticleService.delete(artId, cartId);
            cart_articles = cartArticleService.findAllByIdCart(cartId);
        } else { // Sinon il faut juste effacer l'article du panier.
            cart_articles = (List<CartArticle>) session.getAttribute("articles_in_cart");
            for(CartArticle c : cart_articles){
                if ( c.getArticle().getId() == artId ){
                    cart_articles.remove(c);
                    break;
                }
            }
        }

        // Mettre à jour la session.
        session.setAttribute("articles_in_cart", cart_articles);

        return "redirect:" + referrer;
    }

    @GetMapping("/cart/removeAll")
    public String remove(@RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer,
                         HttpSession session) {

        String cartId = (String) session.getAttribute("userId");
        boolean isConnected = cartId != null;

        List<CartArticle> cart_articles = new LinkedList<>();

        // Si l'utilisateur est connecté. Enlever l'article du panier.
        if (isConnected) {
            cartArticleService.deleteAllByIdCart(cartId);
            cart_articles = cartArticleService.findAllByIdCart(cartId);
        }

        // Mettre à jour la session.
        session.setAttribute("articles_in_cart", cart_articles);

        return "redirect:" + referrer;
    }

    @PostMapping("/cart/update/{artId}")
    public String updateQuantity(HttpSession session,
                                 @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer,
                                 @PathVariable("artId") int artId,
                                 @RequestParam("quantity") Integer quantity) {
        // Si l'utilisateur est connecté.
        String cartId = (String) session.getAttribute("userId");
        boolean isConnected = cartId != null;
        boolean toRemove = quantity < 1;

        if (toRemove) {
            return remove(session,referrer,artId);
        }

        List<CartArticle> cart_articles;
        CartArticle cart_article = null;

        // Si l'utilisateur est connecté. Mettre à jour son panier.
        if (isConnected) {
            cart_article = cartArticleService.find(artId, cartId).orElse(null);
            if (cart_article == null) {
                add(session, referrer, artId, 1);
                cart_article = cartArticleService.find(artId, cartId).orElse(null);
            }
            if (cart_article == null) { // Cannot add article to user.
                return "redirect:" + referrer;
            }

            cart_article.setQuantity(quantity);
            cartArticleService.save(cart_article);   // update
            cart_articles = cartArticleService.findAllByIdCart(cartId);
        } else { // sinon il faut traiter le panier de la session.
            cart_articles = (List<CartArticle>) session.getAttribute("articles_in_cart");
            for(CartArticle c : cart_articles){
                if ( c.getArticle().getId() == artId ){
                    cart_article = c;
                    c.setQuantity(quantity);
                    break;
                }
            }

            if (cart_article == null) {
                add(session, referrer, artId, 1);
                cart_articles = (List<CartArticle>) session.getAttribute("articles_in_cart");
                for(CartArticle c : cart_articles){
                    if ( c.getArticle().getId() == artId ){
                        c.setQuantity(quantity);
                        break;
                    }
                }
            }
        }

        // Mettre à jour la session.
        session.setAttribute("articles_in_cart", cart_articles);

        return "redirect:" + referrer;
    }
}
