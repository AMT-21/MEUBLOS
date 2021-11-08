package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Categorie")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nomCategorie")
    private String nameCategory;

    @ManyToMany
    Set<Furniture> furnitures;

    public Category(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
