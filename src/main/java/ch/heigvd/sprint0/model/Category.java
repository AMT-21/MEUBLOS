package ch.heigvd.sprint0.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Categorie")
public class Category {
    @Id
    @Column(name = "nomCategorie")
    private String nameCategory;

    @OneToMany(mappedBy = "ids.category")
    private Set<ArticleCategory> article_category_list;

    public Category(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public Category() { }
}
