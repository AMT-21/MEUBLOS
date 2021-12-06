package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Cart;
import ch.heigvd.sprint0.model.CartArticle;
import ch.heigvd.sprint0.model.CartArticleIds;
import ch.heigvd.sprint0.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICartArticleService cartArticleService;

    @Autowired
    private ICartService cartService;

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

        Cart cart = isConnected ? cartService.findById(cartId).get() : null;
        Article article = articleService.findById(artId).get();
        CartArticleIds ids = new CartArticleIds(cart, article);

        List<CartArticle> cart_articles;

        CartArticle objToAdd = new CartArticle(ids, quantity);

        // Rajoute l'article au panier de l'utilisateur ou dans le panier de la session
        if (cart != null) {
            cartArticleService.save(objToAdd);
            cart_articles = cartArticleService.findCartArticlesByIdCart(cartId);
        } else { // sinon il faut traité le panier de la session.
            cart_articles = (List<CartArticle>) session.getAttribute("articles_in_cart");
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

        Cart cart = isConnected ? cartService.findById(cartId).get() : null;
        Optional<Article> opArticle = articleService.findById(artId);
        if (!opArticle.isPresent()) {
            return "redirect:/cart";
        }
        Article article = opArticle.get();


        List<CartArticle> cart_articles;

        // Si l'utilisateur est connecté. Enlever l'article du panier.
        if (cart != null) {
            cartArticleService.deleteCartArticleByIdArticleAndIdCart(article.getId(), cartId);
            cart_articles = cartArticleService.findCartArticlesByIdCart(cartId);
        } else { // sinon il faut juste effacer l'article du panier.
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

        return "redirect:/cart";
    }

    @GetMapping("/cart/removeAll")
    public String remove(Model model, HttpSession session) {

        String cartId = (String) session.getAttribute("userId");
        boolean isConnected = cartId != null;

        List<CartArticle> cart_articles = new LinkedList<>();

        // Si l'utilisateur est connecté. Enlever l'article du panier.
        if (isConnected) {
            cartArticleService.deleteCartArticlesByIdCart(cartId);
            cart_articles = cartArticleService.findCartArticlesByIdCart(cartId);
        }

        // Mettre à jour la session.
        session.setAttribute("articles_in_cart", cart_articles);

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

        List<CartArticle> cart_articles;
        Optional<CartArticle> op_cart_article;
        CartArticle cart_article = null;

        // Si l'utilisateur est connecté. Mettre à jour son panier.
        if (isConnected) {
            op_cart_article = cartArticleService.findCartArticleByIdArticleAndIdCart(artId, cartId);
            if (!op_cart_article.isPresent()) {
                add(model, session, artId, 1);
                cart_article = cartArticleService.findCartArticleByIdArticleAndIdCart(artId, cartId).get();
            } else {
                cart_article = op_cart_article.get();
            }

            cart_article.setQuantity(quantity);
            cartArticleService.save(cart_article);   // update
            cart_articles = cartArticleService.findCartArticlesByIdCart(cartId);
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
                add(model, session, artId, 1);
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

        return "redirect:/cart";
    }

    // Récupère le contenu du panier si déjà dans la session. Sinon la crée ou l'ajoute à l'utilisateur connecté
    private List<CartArticle> getCartArticles(HttpSession session) {
        List<CartArticle> cart_articles = (List<CartArticle>) session.getAttribute("articles_in_cart");
        if (cart_articles == null) { // Si session vide
            cart_articles = new LinkedList<>();

            // Récupère le panier de l'utilisateur si connecté.
            String cartId = (String) session.getAttribute("userId");
            if (cartId != null) {
                cart_articles.addAll(cartArticleService.findCartArticlesByIdCart(cartId));
            }

            // Met à jour la session
            session.setAttribute("articles_in_cart", cart_articles);
        }

        return cart_articles;
    }
}
