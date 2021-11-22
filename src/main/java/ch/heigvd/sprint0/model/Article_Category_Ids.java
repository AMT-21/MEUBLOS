package ch.heigvd.sprint0.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;


// Composite key :
// https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#identifiers-composite
@Embeddable
public class Article_Category_Ids implements Serializable {

    @ManyToOne
    @JoinColumn(name = "idMeuble", referencedColumnName = "id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "nomCategorie", referencedColumnName = "nomCategorie")
    private Category category;

    public Article_Category_Ids(Article article, Category category) {
        this.article = article;
        this.category = category;
    }

    public Article_Category_Ids() { }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article_Category_Ids that = (Article_Category_Ids) o;
        return Objects.equals(article, that.article) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article, category);
    }
}
