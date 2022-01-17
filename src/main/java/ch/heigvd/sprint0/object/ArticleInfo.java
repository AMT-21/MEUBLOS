package ch.heigvd.sprint0.object;

import ch.heigvd.sprint0.model.Article;
import ch.heigvd.sprint0.model.CartArticle;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ArticleInfo {
    Article article;
    CartArticle cart_article;
    boolean canBePurchase;
    boolean isAvailable;
    boolean isInCart;
    int quantity;

    public ArticleInfo(Article article) {
        this(article, null);
    }

    public ArticleInfo(Article article, CartArticle cart_article) {
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

    public int getQuantity() {
        return cart_article.getQuantity();
    }
}
