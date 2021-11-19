package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Panier")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private int idUser;

    @OneToMany(mappedBy = "ids.cart")
    private Set<Cart_Article> cart_article_list;

    public Cart(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Set<Cart_Article> getCart_article_list() {
        return cart_article_list;
    }

    public void setCart_article_list(Set<Cart_Article> cart_article_list) {
        this.cart_article_list = cart_article_list;
    }
}
