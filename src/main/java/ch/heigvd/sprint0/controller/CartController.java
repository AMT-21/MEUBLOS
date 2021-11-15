package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Cart_Article;
import ch.heigvd.sprint0.repository.ArticleRepository;
import ch.heigvd.sprint0.repository.CartArticleRepository;
import ch.heigvd.sprint0.repository.CartRepository;
import ch.heigvd.sprint0.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String index(Model model) {
        List<Cart_Article> cart_articles = new LinkedList<>();
        if (model.containsAttribute("articles_in_cart")) {
            cart_articles = (List<Cart_Article>) model.getAttribute("articles_in_cart");
        }

        if (model.containsAttribute("user_id")) {
            cart_articles.addAll(articleService.findCartArticleFromUser((Long) model.getAttribute("user_id")));
        }

        model.addAttribute("articles_in_cart", cart_articles);
        return "cart.html";
    }


    @GetMapping("/cart/remove/{idArt}/{idCart}")
    public String remove(Model model, @PathVariable("idArt") long idArt, @PathVariable("idCart") long idCart) {
        cartArticleRepository.delete(
                cartArticleRepository.findCart_ArticleByArticleAndCart(
                        articleRepository.findById(idArt).get(),
                        cartRepository.findById(idCart).get()));
        return "redirect:/cart";
    }
}
