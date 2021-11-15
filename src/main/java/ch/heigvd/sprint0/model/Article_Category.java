package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article_Category that = (Article_Category) o;
        return Objects.equals(article, that.article) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article, category);
    }
}
