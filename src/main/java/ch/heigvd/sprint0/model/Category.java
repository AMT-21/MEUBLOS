package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Categorie")
public class Category {
    @Id
    @Column(name = "nomCategorie")
    private String nameCategory;

    @OneToMany(mappedBy = "category")
    private Set<Article_Category> article_category_list;

    public Category(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
