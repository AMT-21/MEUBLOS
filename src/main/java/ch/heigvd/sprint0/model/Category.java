package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Categorie")
public class Category {
    @Id
    @Column(name = "nomCategorie")
    private String nameCategory;

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    @OneToMany(mappedBy = "ids.category")
    private Set<ArticleCategory> article_category_list;

    public Category(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public Category() { }
}
