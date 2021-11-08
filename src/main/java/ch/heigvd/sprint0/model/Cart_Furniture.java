package ch.heigvd.sprint0.model;

import javax.persistence.*;

@Entity
@Table(name = "Panier_Meuble")
public class Cart_Furniture {
    @Id
    @ManyToOne
    @JoinColumn(name = "idMeuble", referencedColumnName = "id")
    private Furniture furniture;

    @Id
    @ManyToOne
    @JoinColumn(name = "idUserPanier", referencedColumnName = "idUser")
    private Cart cart;

    @Column(name = "quantite")
    private int quantity;
}
