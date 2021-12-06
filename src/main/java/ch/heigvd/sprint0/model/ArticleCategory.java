package ch.heigvd.sprint0.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Meuble_Categorie")
public class ArticleCategory implements Serializable {
    @EmbeddedId
    private ArticleCategoryIds ids;

    public ArticleCategory(ArticleCategoryIds ids) {
        this.ids = ids;
    }

    public ArticleCategory() { }

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
        ArticleCategory that = (ArticleCategory) o;
        return Objects.equals(ids, that.ids);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ids);
    }
}
