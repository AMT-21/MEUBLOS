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

    @OneToMany(mappedBy = "cart")
    private Set<Cart_Article> cart_article_list;

    public Cart(int idUser) {
        this.idUser = idUser;
    }
}
