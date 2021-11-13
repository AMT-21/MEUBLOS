package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Panier_Meuble")
public class Cart_Article implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "idMeuble", referencedColumnName = "id")
    private Article article;

    @Id
    @ManyToOne
    @JoinColumn(name = "idUserPanier", referencedColumnName = "idUser")
    private Cart cart;

    @Column(name = "quantite")
    private int quantity;
}
