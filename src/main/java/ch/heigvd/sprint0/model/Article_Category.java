package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Meuble_Categorie")
public class Article_Category implements Serializable {
    @EmbeddedId
    private Article_Category_Ids ids;

    public Article_Category(Article_Category_Ids ids) {
        this.ids = ids;
    }

    public Article_Category() { }

    public Article getArticle() {
        return ids.getArticle();
    }

    public Category getCategory() {
        return ids.getCategory();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article_Category that = (Article_Category) o;
        return Objects.equals(ids, that.ids);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ids);
    }
}
