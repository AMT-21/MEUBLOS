package ch.heigvd.sprint0.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Panier")
public class Cart {
    @Id
    @Column(name = "idUser")
    private String idUser;

    @OneToMany(mappedBy = "ids.cart")
    private Set<CartArticle> cart_article_list;

    public Cart() { }

    public Cart(String idUser) {
        this.idUser = idUser;
    }
}
