package ch.heigvd.sprint0.object;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.Cart_Article;

// TODO DPO - Si ça vous intéresse, je vous invite à vous documenter sur la lib 'lombok' https://projectlombok.org/
//  elle vous simplifiera le code auto-générer et vos classes java seront plus rapides à lire
public class ArticleInfo {
    Article article;
    Cart_Article cart_article;
    boolean canBePurchase;
    boolean isAvailable;
    boolean isInCart;
    int quantity;

    public ArticleInfo(Article article) {
        this(article, null);
    }

    public ArticleInfo(Article article, Cart_Article cart_article) {
        this.article = article;
        this.cart_article = cart_article;
        isInCart = cart_article != null;
        quantity = isInCart ? cart_article.getQuantity() : 0;
        isAvailable = article.getStock() > 0;
        canBePurchase = article.getPrice() > 0 && isAvailable;
    }

    public Integer getId() {
        return article.getId();
    }

    public String getName() {
        return article.getName();
    }

    public String getDescription() {
        return article.getDescription();
    }

    public double getPrice() {
        return article.getPrice();
    }

    public int getStock() {
        return article.getStock();
    }

    public boolean getCanBePurchase() {
        return canBePurchase;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public boolean getIsInCart() {
        return isInCart;
    }

    public int getQuantity() {
        return cart_article.getQuantity();
    }
}
