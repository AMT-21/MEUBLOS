package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Categorie")
public class Category {
    @Id
    @Column(name = "nomCategorie")
    private String nameCategory;

    public String getNameCategory() {
        return nameCategory;
    }

    @OneToMany(mappedBy = "ids.category")
    private Set<Article_Category> article_category_list;

    public Category(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    /*public Category(String nameCategory, Set<Article_Category> article_category_list) {
        this.nameCategory = nameCategory;
        this.article_category_list = article_category_list;
    }*/

    public Category() { }
}
