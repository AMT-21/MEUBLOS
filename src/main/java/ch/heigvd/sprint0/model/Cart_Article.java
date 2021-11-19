package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Panier_Meuble")
public class Cart_Article implements Serializable {

    @EmbeddedId
    private Cart_Article_Ids ids;

    @Column(name = "quantite")
    private int quantity;

    public Cart_Article_Ids getIds() {
        return ids;
    }

    public void setIds(Cart_Article_Ids ids) {
        this.ids = ids;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
