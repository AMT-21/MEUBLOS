package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Panier_Meuble")
public class CartArticle implements Serializable {

    @EmbeddedId
    private CartArticleIds ids;

    @Column(name = "quantite")
    private int quantity;

    public CartArticle(CartArticleIds ids, int quantity) {
        this.ids = ids;
        this.quantity = quantity;
    }

    public Cart getCart() {
        return ids.getCart();
    }

    public void setCart(Cart cart) {
        ids.setCart(cart);
    }

    public Article getArticle() {
        return ids.getArticle();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartArticle that = (CartArticle) o;
        return quantity == that.quantity && Objects.equals(ids, that.ids);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ids, quantity);
    }
}
