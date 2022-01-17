package ch.heigvd.sprint0.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;


// Composite key :
// https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#identifiers-composite
@Embeddable
@Getter
@Setter
public class ArticleCategoryIds implements Serializable {

    @ManyToOne
    @JoinColumn(name = "idMeuble", referencedColumnName = "id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "nomCategorie", referencedColumnName = "nomCategorie")
    private Category category;

    public ArticleCategoryIds(Article article, Category category) {
        this.article = article;
        this.category = category;
    }

    public ArticleCategoryIds() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleCategoryIds that = (ArticleCategoryIds) o;
        return Objects.equals(article, that.article) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article, category);
    }
}
