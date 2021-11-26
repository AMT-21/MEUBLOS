package ch.heigvd.sprint0.object;

import ch.heigvd.sprint0.model.Article;

public class ArticleInfo {
    Article article;
    boolean canBePurchase;

    ArticleInfo(Article article) {
        this.article = article;
        canBePurchase = article.getPrice() > 0 && article.getStock() > 0;
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
}
