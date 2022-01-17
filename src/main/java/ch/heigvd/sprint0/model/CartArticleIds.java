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
public class CartArticleIds implements Serializable {

    @ManyToOne
    @JoinColumn(name = "idUserPanier", referencedColumnName = "idUser")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "idMeuble", referencedColumnName = "id")
    private Article article;

    public CartArticleIds(Cart cart, Article article) {
        this.cart = cart;
        this.article = article;
    }

    public CartArticleIds() { }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        CartArticleIds ids = (CartArticleIds) o;
        return Objects.equals( cart, ids.cart ) &&
                Objects.equals( article, ids.article );
    }

    @Override
    public int hashCode() {
        return Objects.hash( cart, article );
    }
}
