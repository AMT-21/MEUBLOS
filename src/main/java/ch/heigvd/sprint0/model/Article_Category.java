package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Meuble_Categorie")
public class Article_Category implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "idMeuble", referencedColumnName = "id")
    private Article article;

    @Id
    @ManyToOne
    @JoinColumn(name = "nomCategorie", referencedColumnName = "nomCategorie")
    private Category category;
}
