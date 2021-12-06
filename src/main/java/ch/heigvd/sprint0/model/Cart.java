package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Panier")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private String idUser;

    @OneToMany(mappedBy = "ids.cart")
    private Set<CartArticle> cart_article_list;

    public Cart(String idUser) {
        this.idUser = idUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public Set<CartArticle> getCart_article_list() {
        return cart_article_list;
    }

    public void setCart_article_list(Set<CartArticle> cart_article_list) {
        this.cart_article_list = cart_article_list;
    }
}
